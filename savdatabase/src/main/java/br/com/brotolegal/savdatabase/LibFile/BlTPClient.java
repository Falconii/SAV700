package br.com.brotolegal.savdatabase.LibFile;

import android.content.Context;
import android.util.Log;

import org.apache.commons.net.ftp.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BlTPClient {
	
	private final String TAG = "FTP";
	
	public FTPClient mFTPClient = null; 

	//Method to connect to FTP server:
	public boolean ftpConnect(String host, String username,String password, int port) throws IOException,Exception{
	
    	boolean status = false;

		try {
	    	
		        mFTPClient = new FTPClient();

		        mFTPClient.connect(host, port);
	
		        // now check the reply code, if positive mean connection success
		        if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
		            // login using username & password
		            status = mFTPClient.login(username, password);
	
		            /* Set File Transfer Mode
		             *
		             * To avoid corruption issue you must specified a correct
		             * transfer mode, such as ASCII_FILE_TYPE, BINARY_FILE_TYPE,
		             * EBCDIC_FILE_TYPE .etc. Here, I use BINARY_FILE_TYPE
		             * for transferring text, image, and compressed files.
		             */
		            mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
		            mFTPClient.enterLocalPassiveMode();
	
		        }
		        
	    } catch(IOException e ) {
	        
	    	throw new IOException(e.getMessage()); 		
	    	
	    } catch (Exception e) {
	    	
	    	throw new Exception(e.getMessage());	
	    	
		}

	 
		return status;
	    
	} 

	//Method to disconnect from FTP server:

	public boolean ftpDisconnect()  
	{
	    try {
	    	
	        mFTPClient.logout();
	        
	        mFTPClient.disconnect();
	        
	        return true;
	        
	    } catch (Exception e) {
	    	
	        return false;
	    }

	} 

	//Method to get current working directory:

	public String ftpGetCurrentWorkingDirectory()
	{
	    try {
	        String workingDir = mFTPClient.printWorkingDirectory();
	        return workingDir;
	    } catch(Exception e) {
	        Log.d(TAG, "Error: could not get current working directory.");
	    }

	    return null;
	} 

	//Method to change working directory:

	public boolean ftpChangeDirectory(String directory_path)
	{
	    try {
	        mFTPClient.changeWorkingDirectory(directory_path);
	    } catch(Exception e) {
	        Log.d(TAG, "Error: could not change directory to " + directory_path);
	    }

	    return false;
	} 

	//Method to list all files in a directory:

	public void ftpPrintFilesList(String dir_path)
	{
	    try {
	        FTPFile[] ftpFiles = mFTPClient.listFiles(dir_path);
	        int length = ftpFiles.length;

	        for (int i = 0; i < length; i++) {
	            String name = ftpFiles[i].getName();
	            boolean isFile = ftpFiles[i].isFile();

	            if (isFile) {
	                Log.i(TAG, "File : " + name);
	            }
	            else {
	                Log.i(TAG, "Directory : " + name);
	            }
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	} 

	public boolean ftpExistFile(String dir_path,String Filename){

		boolean retorno = false;

		int tentativas  = 5;

		try {

			while (tentativas > 0) {

				TimeUnit.SECONDS.sleep(2);

				FTPFile[] ftpFiles = mFTPClient.listFiles(dir_path);

				int length = ftpFiles.length;

				for (int i = 0; i < length; i++) {

					Log.i("MyFirebaseMsgService","Tentativa "+String.valueOf(i));

					if (Filename.trim().toUpperCase().equals(ftpFiles[i].getName().toUpperCase().trim())) {


						Log.i("MyFirebaseMsgService","Achou O Arquivo...");

						retorno = true;

						tentativas = 0;

						break;

					}
				}

				tentativas--;
			}

			} catch(Exception e) {

				return retorno;

			}


		return retorno;

	}

	//Method to create new directory:

	public boolean ftpMakeDirectory(String new_dir_path)
	{
	    try {
	        boolean status = mFTPClient.makeDirectory(new_dir_path);
	        return status;
	    } catch(Exception e) {
	        Log.d(TAG, "Error: could not create new directory named " + new_dir_path);
	    }

	 return false;
	} 

	//Method to delete/remove a directory:

	public boolean ftpRemoveDirectory(String dir_path)
	{
	    try {
	        boolean status = mFTPClient.removeDirectory(dir_path);
	        return status;
	    } catch(Exception e) {
	        Log.d(TAG, "Error: could not remove directory named " + dir_path);
	    }

	    return false;
	} 

	//Method to delete a file:

	public void ftpRemoveFile(String filePath) throws Exception{

	    mFTPClient.deleteFile(filePath);
	}

	//Method to rename a file:

	public  boolean ftpRenameFile(String from, String to)
	{
	    try {
	        boolean status = mFTPClient.rename(from, to);
	        return status;
	    } catch (Exception e) {
	        Log.d(TAG, "Could not rename file: " + from + " to: " + to);
	    }

	    return false;
	} 

	//Method to download a file from FTP server:

	/**
	 * mFTPClient: FTP client connection object (see FTP connection example)
	 * srcFilePath: path to the source file in FTP server
	 * desFilePath: path to the destination file to be saved in sdcard
	 */
	public void ftpDownload(String srcFilePath, String desFilePath) throws Exception
	{
	    boolean status = false;

	    try {

			FileOutputStream desFileStream = new FileOutputStream(desFilePath);;
	        status = mFTPClient.retrieveFile(srcFilePath, desFileStream);
	        desFileStream.close();

	    } catch (Exception e) {

			throw new Exception(e.getMessage());

	    }

	}

	//Method to upload a file to FTP server:

	/**
	 * mFTPClient: FTP client connection object (see FTP connection example)
	 * srcFilePath: source file path in sdcard
	 * desFileName: file name to be stored in FTP server
	 * desDirectory: directory path where the file should be upload to
	 */
	public boolean ftpUpload(String srcFilePath, String desFileName,
	                         String desDirectory, Context context)
	{
	    boolean status = false;
	    try {
	       // FileInputStream srcFileStream = new FileInputStream(srcFilePath);
	        
	        FileInputStream srcFileStream = context.openFileInput(srcFilePath);

	        // change working directory to the destination directory
	        //if (ftpChangeDirectory(desDirectory)) {
	            status = mFTPClient.storeFile(desFileName, srcFileStream);
	        //}

	        srcFileStream.close();
	        return status;
	    } 
	    catch (Exception e) {
	        Log.d(TAG, "upload failed: " + e);
	    }

	    return status;
	}
}