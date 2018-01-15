/* http://www.sefaz.mt.gov.br/portal/Tributario/Digito_verificador/Default.php */

package br.com.brotolegal.savdatabase.regrasdenegocio;

public  class ValidaIE {

    public static boolean isIE(String ie, String uf) {
        try
        {

            if (ie.equals("ISENTO")){

                return true;
            }

            if (ie.equals("0000000000000")){

                return true;
            }


            return valida(ie, uf);

        }
        catch (Exception e){

            return false;

        }



    }

    public static boolean valida(String inscricaoEstadual, String siglaUf) {
        String strIE = inscricaoEstadual;
        siglaUf = siglaUf.toUpperCase();

        if(siglaUf.equals("SP")){

            return validaIESaoPaulo(strIE);

        }

        if(siglaUf.equals("MG")){

            return validaIEMinasGerais(strIE);

        }

        if (siglaUf.equals("PR")){

            return validaIEParana(strIE);

        }


        if (siglaUf.equals("GO")){

            return validaIEGoias(strIE);

        }

        return false;
		
		
		
/*		if(siglaUf.equals("AC")){ 
		return validaIEAcre(strIE); 
		} else if(siglaUf.equals("AL")){ 
		return validaIEAlagoas(strIE); 
		} else if(siglaUf.equals("AP")){ 
		validaIEAmapa(strIE); 
		} else if(siglaUf.equals("AM")){ 
		validaIEAmazonas(strIE); 
		} else if(siglaUf.equals("BA")){ 
		validaIEBahia(strIE); 
		} else if(siglaUf.equals("CE")){ 
		validaIECeara(strIE); 
		} else if(siglaUf.equals("ES")){ 
		validaIEEspiritoSanto(strIE); 
		} else if(siglaUf.equals("GO")){ 
		validaIEGoias(strIE); 
		} else if(siglaUf.equals("MA")){ 
		validaIEMaranhao(strIE); 
		} else if(siglaUf.equals("MT")){ 
		validaIEMatoGrosso(strIE); 
		} else if(siglaUf.equals("MS")){ 
		validaIEMatoGrossoSul(strIE); 
		} else if(siglaUf.equals("MG")){ 
		validaIEMinasGerais(strIE); 
		} else if(siglaUf.equals("PA")){ 
		validaIEPara(strIE); 
		} else if(siglaUf.equals("PB")){ 
		validaIEParaiba(strIE); 
		} else if(siglaUf.equals("PR")){ 
		validaIEParana(strIE); 
		} else if(siglaUf.equals("PE")){ 
		validaIEPernambuco(strIE); 
		} else if(siglaUf.equals("PI")){ 
		validaIEPiaui(strIE); 
		} else if(siglaUf.equals("RJ")){ 
		validaIERioJaneiro(strIE); 
		} else if(siglaUf.equals("RN")){ 
		validaIERioGrandeNorte(strIE); 
		} else if(siglaUf.equals("RS")){ 
		validaIERioGrandeSul(strIE); 
		} else if(siglaUf.equals("RO")){ 
		validaIERondonia(strIE); 
		} else if(siglaUf.equals("RR")){ 
		validaIERoraima(strIE); 
		} else if(siglaUf.equals("SC")){ 
		validaIESantaCatarina(strIE);
		
		} else */
		 /*  
		} else if(siglaUf.equals("SE")){ 
		validaIESergipe(strIE); 
		} else if(siglaUf.equals("TO")){ 
		validaIETocantins(strIE); 
		} else if(siglaUf.equals("DF")){ 
		validaIEDistritoFederal(strIE); 
		}*/


    }


    /**
     * Valida inscri��o estadual do estado do Acre
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEAcre(String ie) throws Exception { //inic�o do validaIEAcre()
        //valida a quantidade de d�gitos
        if (ie.length() != 13){
            throw new Exception("Quantidade de digitos inválida.");
        }

        //valida os dois primeiros digitos - devem ser iguais a 01
        for(int i = 0; i < 2; i++){
            if (Integer.parseInt(String.valueOf(ie.charAt(i))) != i){
                throw new Exception("Inscrição Estadual inválida");
            }
        }

        int soma = 0;
        int pesoInicial = 4;
        int pesoFinal = 9;
        int d1 = 0; //primeiro digito verificador
        int d2 = 0; //segundo digito verificador

        //calcula o primeiro digito
        for(int i = 0; i < ie.length() - 2; i++){
            if (i < 3){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
                pesoInicial--;
            }
            else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
                pesoFinal--;
            }
        }
        d1 = 11 - (soma % 11);
        if (d1 == 10 || d1 == 11){
            d1 = 0;
        }

        //calcula o segundo digito
        soma = d1 * 2;
        pesoInicial = 5;
        pesoFinal = 9;
        for(int i = 0; i < ie.length() - 2; i++){
            if (i < 4){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
                pesoInicial--;
            }
            else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
                pesoFinal--;
            }
        }

        d2 = 11 - (soma % 11);
        if (d2 == 10 || d2 == 11){
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))){
            throw new Exception("Digito verificador inválido.");
        }
    } //fim do validaIEAcre()

    /**
     * Valida inscri��o estadual do estado do Alagoas
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEAlagoas(String ie) throws Exception{
        //valida quantidade de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de d�gitos inv�lida.");
        }

        //valida os dois primeiros d�gitos - deve ser iguais a 24
        if (!ie.substring(0, 2).equals("24")){
            throw new Exception("Inscrição estadual inválida.");
        }

        //valida o terceiro d�gito - deve ser 0,3,5,7,8
        int[] digits = {0,3,5,7,8};
        boolean check = false;
        for(int i = 0; i < digits.length; i++){
            if (Integer.parseInt(String.valueOf(ie.charAt(2))) == digits[i]){
                check = true;
                break;
            }
        }
        if (!check){
            throw new Exception("Inscrição estadual inválida.");
        }

        //calcula o d�gito verificador
        int soma = 0;
        int peso = 9;
        int d = 0; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }
        d = ((soma * 10)%11);
        if (d == 10){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Amap�
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEAmapa(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //verifica os dois primeiros d�gitos - deve ser igual 03
        if (!ie.substring(0, 2).equals("03")){
            throw new Exception("Inscrição estadual inválida.");
        }

        //calcula o d�gito verificador
        int d1 = -1;
        int soma = -1;
        int peso = 9;

        //configura o valor do d�gito verificador e da soma de acordo com faixa das inscri��es
        long x = Long.parseLong(ie.substring(0, ie.length() -1)); //x = inscri��o estadual sem o d�gito verificador
        if (x >= 3017001L && x <= 3019022L){
            d1 = 1;
            soma = 9;
        } else if (x >= 3000001L && x <= 3017000L){
            d1 = 0;
            soma = 5;
        } else if (x >= 3019023L){
            d1 = 0;
            soma = 0;
        }

        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int d = 11 - ((soma % 11)); //d = armazena o d�gito verificador ap�s c�lculo
        if (d == 10){
            d = 0;
        } else if (d == 11){
            d = d1;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Amazonas
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEAmazonas(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for (int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        if (soma < 11){
            d = 11 - soma;
        } else if ((soma % 11) <= 1){
            d = 0;
        } else {
            d = 11 - (soma % 11);
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Bahia
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEBahia(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 8 && ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas." + ie);
        }

        //C�lculo do m�dulo de acordo com o primeiro d�gito da inscri��o Estadual
        int modulo = 10;
        int firstDigit = Integer.parseInt(String.valueOf(ie.charAt(ie.length()==8 ? 0 : 1 ))) ;
        if(firstDigit == 6 || firstDigit == 7 || firstDigit == 9)
            modulo = 11;

        //C�lculo do segundo d�gito
        int d2 = -1; //segundo d�gito verificador
        int soma = 0;
        int peso = ie.length()==8 ? 7 : 8;
        for(int i = 0; i < ie.length() - 2; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int resto = soma % modulo;

        if (resto == 0 || (modulo == 11 && resto == 1)){
            d2 = 0;
        } else {
            d2 = modulo - resto;
        }

        //C�lculo do primeiro d�gito
        int d1 = -1; //primeiro d�gito verificador
        soma = d2 * 2;
        peso = ie.length()==8 ? 8 : 9;
        for(int i = 0; i < ie.length() - 2; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        resto = soma % modulo;

        if (resto == 0 || (modulo == 11 && resto == 1)){
            d1 = 0;
        } else {
            d1 = modulo - resto;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))){
            throw new Exception("Digito verificador inválido." + ie);
        }
    }

    /**
     * Valida inscri��o estadual do estado do Cear�
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIECeara(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //C�lculo do d�gito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if(d == 10 || d == 11){
            d = 0;
        }
        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Esp�rito Santo
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEEspiritoSanto(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //C�lculo do d�gito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int resto = soma % 11;
        if (resto < 2){
            d = 0;
        } else if (resto > 1){
            d = 11 - resto;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscricao estadual do estado do Goias
     * @param ie (Inscricao estadual)
     * @throws Exception
     */
    private  static Boolean validaIEGoias(String ie){
        //valida quantida de d�gitos
        if (ie.length() != 9){

            return false;

        }

        //v�lida os dois primeiros d�gito
        if (!"10".equals(ie.substring(0, 2))){
            if (!"11".equals(ie.substring(0, 2))){
                if (!"15".equals(ie.substring(0, 2))){

                    return false;


                }
            }
        }

        if (ie.substring(0, ie.length() - 1).equals("11094402")){
            if (!ie.substring(ie.length() - 1, ie.length()).equals("0")){
                if (!ie.substring(ie.length() - 1, ie.length()).equals("1")){
                    return false;
                }
            }
        } else {

            //C�lculo do d�gito verificador
            int soma = 0;
            int peso = 9;
            int d = -1; //d�gito verificador
            for(int i = 0; i < ie.length() - 1; i++){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }

            int resto = soma % 11;
            long faixaInicio = 10103105;
            long faixaFim = 10119997;
            long insc = Long.parseLong(ie.substring(0, ie.length() -1));
            if (resto == 0){
                d = 0;
            } else if (resto == 1){
                if (insc >= faixaInicio && insc <= faixaFim){
                    d = 1;
                } else {
                    d = 0;
                }
            } else if (resto != 0 && resto != 1){
                d = 11 - resto;
            }

            //valida o digito verificador
            String dv = d + "";
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){

                return false;

            }
        }

        return true;
    }

    /**
     * Valida inscri��o estadual do estado do Maranh�o
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEMaranhao(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros d�gitos
        if(!ie.substring(0, 2).equals("12")){
            throw new Exception("Inscrição estadual inválida.");
        }

        //Calcula o d�gito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Mato Grosso
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEMatoGrosso(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 11){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Calcula o d�gito verificador
        int soma = 0;
        int pesoInicial = 3;
        int pesoFinal = 9;
        int d = -1;

        for(int i = 0; i < ie.length() - 1; i++){
            if (i < 2){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
                pesoInicial--;
            }
            else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
                pesoFinal--;
            }
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 ||(soma % 11) == 1){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Mato Grosso do Sul
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEMatoGrossoSul(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros d�gitos
        if (!ie.substring(0, 2).equals("28")){
            throw new Exception("Inscrição estadual inválida.");
        }

        //Calcula o d�gito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int resto = soma % 11;
        int result = 11 - resto;
        if (resto == 0){
            d = 0;
        }
        else if (resto > 0){
            if(result > 9){
                d = 0;
            } else if (result < 10){
                d = result;
            }
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Minas Gerais
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static boolean validaIEMinasGerais(String ie)  {
		/* 
		 * FORMATO GERAL: A1A2A3B1B2B3B4B5B6C1C2D1D2 
		 * Onde: A= C�digo do Munic�pio 
		 * B= N�mero da inscri��o 
		 * C= N�mero de ordem do estabelecimento 
		 * D= D�gitos de controle 
		 */

        // valida quantida de d�gitos
        if (ie.length() != 13) {

            return false;

        }

        //iguala a casas para o c�lculo
        //em inserir o algarismo zero "0" imediatamente ap�s o n�mero de c�digo do munic�pio,
        //desprezando-se os d�gitos de controle.
        String str = "";
        for(int i = 0; i < ie.length() - 2; i++){
            if (Character.isDigit(ie.charAt(i))){
                if (i == 3){
                    str += "0";
                    str += ie.charAt(i);
                } else {
                    str += ie.charAt(i);
                }
            }
        }

        //C�lculo do primeiro d�gito verificador
        int soma = 0;
        int pesoInicio = 1;
        int pesoFim = 2;
        int d1 = -1; //primeiro d�gito verificador
        for(int i = 0; i < str.length(); i++ ){
            if (i % 2 == 0){
                int x = Integer.parseInt(String.valueOf(str.charAt(i))) * pesoInicio;
                String strX = Integer.toString(x);
                for(int j = 0; j < strX.length(); j++){
                    soma += Integer.parseInt(String.valueOf(strX.charAt(j)));
                }
            } else {
                int y = Integer.parseInt(String.valueOf(str.charAt(i))) * pesoFim;
                String strY = Integer.toString(y);
                for(int j = 0; j < strY.length(); j++){
                    soma += Integer.parseInt(String.valueOf(strY.charAt(j)));
                }
            }
        }

        int dezenaExata = soma;
        while(dezenaExata % 10 != 0){
            dezenaExata++;
        }
        d1 = dezenaExata - soma; //resultado - primeiro d�gito verificador

        //C�lculo do segundo d�gito verificador
        soma = d1 * 2;
        pesoInicio = 3;
        pesoFim = 11;
        int d2 = -1;
        for(int i = 0; i < ie.length() - 2; i++){
            if(i < 2){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11); //resultado - segundo d�gito verificador
        if ((soma % 11 == 0) || (soma % 11 == 1)){
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))){
            return false;
        }

        return true;
    }

    /**
     * Valida inscri��o estadual do estado do Par�
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEPara(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros d�gitos
        if (!ie.substring(0, 2).equals("15")){
            throw new Exception("Inscrição estadual inválida.");
        }

        //Calcula o d�gito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if((soma % 11) == 0 || (soma % 11) == 1){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Para�ba
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEParaiba(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Calcula o d�gito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 10 || d == 11){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscricao estadual do estado do Parana
     * @param ie (Inscricao estadual)
     * @throws Exception
     */
    private static Boolean validaIEParana(String ie){
        //valida quantida de d�gitos
        if (ie.length() != 10){

            return false;

        }

        //C�lculo do primeiro d�gito
        int soma = 0;
        int pesoInicio = 3;
        int pesoFim = 7;
        int d1 = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 2; i++){
            if(i < 2){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d1 = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1){
            d1 = 0;
        }

        //c�lculo do segundo d�gito
        soma = d1 * 2;
        pesoInicio = 4;
        pesoFim = 7;
        int d2 = -1; //segundo d�gito
        for(int i = 0; i < ie.length() - 2; i++){
            if (i < 3){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1){
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))){

            return false;

        }


        return true;

    }

    /**
     * Valida inscri��o estadual do estado do Pernambuco
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEPernambuco(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 14){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //C�lculo do d�gito verificador
        int soma = 0;
        int pesoInicio = 5;
        int pesoFim = 9;
        int d = -1; //d�gito verificador

        for(int i = 0; i < ie.length() - 1; i++){
            if(i < 5){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d = 11 - (soma % 11);
        if (d > 9){
            d -= 10;
        }

        System.out.println(soma);
        System.out.println(11 - (soma % 11));
        System.out.println(d);

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Piau�
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEPiaui(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //C�lculo do d�gito verficador
        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 11 || d == 10){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Rio de Janeiro
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIERioJaneiro(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 8 ){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //C�lculo do d�gito verficador
        int soma = 0;
        int peso = 7;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            if(i == 0){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * 2;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
        }

        d = 11 - (soma % 11);
        if ((soma % 11) <= 1){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Rio Grande do Norte
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIERioGrandeNorte(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 10 && ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros d�gitos
        if (!ie.substring(0, 2).equals("20")){
            throw new Exception("Inscrição estadual inválida.");
        }

        //calcula o d�gito para inscri��o de 9 d�gitos
        if (ie.length() == 9){
            int soma = 0;
            int peso = 9;
            int d = -1; //d�gito verificador
            for(int i = 0; i < ie.length() - 1; i++){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }

            d = ((soma * 10) % 11);
            if (d == 10){
                d = 0;
            }

            //valida o digito verificador
            String dv = d + "";
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
                throw new Exception("Digito verificador inválido.");
            }
        } else {
            int soma = 0;
            int peso = 10;
            int d = -1; //d�gito verificador
            for(int i = 0; i < ie.length() - 1; i++){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
            d = ((soma * 10) % 11);
            if (d == 10){
                d = 0;
            }

            //valida o digito verificador
            String dv = d + "";
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
                throw new Exception("Digito verificador inválido.");
            }
        }

    }

    /**
     * Valida inscri��o estadual do estado do Rio Grande do Sul
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIERioGrandeSul(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 10){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //C�lculo do d�fito verificador
        int soma = Integer.parseInt(String.valueOf(ie.charAt(0))) * 2;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 1; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 10 || d == 11){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Rond�nia
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIERondonia(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 14){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //C�lculo do d�gito verificador
        int soma = 0;
        int pesoInicio = 6;
        int pesoFim = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            if( i < 5){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            }
            else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d = 11 - (soma % 11);
        if(d == 11 || d == 10){
            d -= 10;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Rora�ma
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIERoraima(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros d�gitos
        if (!ie.substring(0, 2).equals("24")){
            throw new Exception("Inscrição estadual inválida.");
        }

        int soma = 0;
        int peso = 1;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso++;
        }

        d = soma % 9;

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Santa Catarina
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIESantaCatarina(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //C�lculo do d�fito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do S�o Paulo
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static boolean validaIESaoPaulo(String ie) {
        //valida quantida de d�gitos
        if (ie.length() != 12 && ie.length() != 13){
            return false;
        }

        if(ie.length() == 12){
            int soma = 0;
            int peso = 1;
            int d1 = -1; //primeiro d�gito verificador
            //Primeiro Digito (Nona Posição)
            for(int i = 0; i < ie.length() - 4; i++){
                if(i == 1 || i == 7){
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * ++peso;
                    peso++;
                } else {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                    peso++;
                }
            }

            d1 = soma % 11;
            String strD1 = Integer.toString(d1); //O d�gito � igual ao algarismo mais a direita do resultado de (soma % 11)
            d1 = Integer.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

            //CALCULO
            soma = 0;
            int pesoInicio = 3;
            int pesoFim = 10;
            int d2 = -1; //segundo d�gito verificador
            for(int i = 0; i < ie.length() - 1; i++){
                if(i < 2){
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                    pesoInicio--;
                } else {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                    pesoFim--;
                }
            }

            d2 = soma % 11;
            String strD2 = Integer.toString(d2); //O d�gito � igual ao algarismo mais a direita do resultado de (soma % 11)
            d2 = Integer.parseInt(String.valueOf(strD2.charAt(strD2.length() - 1)));

            //valida os d�gitos verificadores
            if(!ie.substring(8, 9).equals(d1 + "")){
                return false;
            }
            if(!ie.substring(11, 12).equals(d2 + "")){
                return false;
            }

        } else {
            //valida o primeiro caracter
            if(ie.charAt(0) != 'P'){
                return false;
            }

            String strIE = ie.substring(1, 10); //Obt�m somente os d�gitos utilizados no c�lculo do d�gito verificador
            int soma = 0;
            int peso = 1;
            int d1 = -1; //primeiro d�gito verificador
            //c�lculo do primeiro d�gito verificador (nona posi��o)
            for(int i = 0; i < strIE.length() - 1; i++){
                if(i == 1 || i == 7){
                    soma += Integer.parseInt(String.valueOf(strIE.charAt(i))) * ++peso;
                    peso++;
                } else {
                    soma += Integer.parseInt(String.valueOf(strIE.charAt(i))) * peso;
                    peso++;
                }
            }

            d1 = soma % 11;
            String strD1 = Integer.toString(d1); //O d�gito � igual ao algarismo mais a direita do resultado de (soma % 11)
            d1 = Integer.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

            //valida o d�gito verificador
            if(!ie.substring(9, 10).equals(d1 + "")){
                return false;
            } else {
                return true;
            }
        }

        return true;
    }

    /**
     * Valida inscri��o estadual do estado do Sergipe
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIESergipe(String ie) throws Exception{
        //valida quantida de d�gitos
        if (ie.length() != 9){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //c�lculo do d�gito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if(d == 11 || d == 11 || d == 10){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Tocantins
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIETocantins(String ie) throws Exception {
        //valida quantida de d�gitos
        if (ie.length() != 9 && ie.length() != 11){
            throw new Exception("Quantidade de d�gitos inv�lida.");
        } else if (ie.length() == 9) {
            ie = ie.substring(0, 2) + "02" + ie.substring(2);
        }

        int soma = 0;
        int peso = 9;
        int d = -1; //d�gito verificador
        for(int i = 0; i < ie.length() - 1; i++){
            if(i != 2 && i != 3){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
        }
        d = 11 - (soma % 11);
        if ((soma % 11) < 2){
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscri��o estadual do estado do Distrito Federal
     * @param ie (Inscri��o estadual)
     * @throws Exception
     */
    private static void validaIEDistritoFederal(String ie) throws Exception {
        //valida quantida de d�gitos
        if (ie.length() != 13){
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //c�lculo do primeiro d�gito verificador
        int soma = 0;
        int pesoInicio = 4;
        int pesoFim = 9;
        int d1 = -1; //primeiro d�gito verificador
        for(int i = 0; i < ie.length() - 2; i++){
            if(i < 3){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d1 = 11 - (soma % 11);
        if(d1 == 11 || d1 == 10){
            d1 = 0;
        }

        //c�lculo do segundo d�gito verificador
        soma = d1 * 2;
        pesoInicio = 5;
        pesoFim = 9;
        int d2 = -1; //segundo d�gito verificador
        for(int i = 0; i < ie.length() - 2; i++){
            if (i < 4){
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11);
        if(d2 == 11 || d2 == 10){
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))){
            throw new Exception("Digito verificador inválido.");
        }
    }
} //fim da defiçãoo de classe 


		
		   
			
		
	


