package br.com.brotolegal.savdatabase.util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.text.DecimalFormat;

/**
 * Created by Falconi on 27/06/2017.
 */

public class FormatCoordenadasFDS implements IAxisValueFormatter {

        private DecimalFormat mFormat;

        public FormatCoordenadasFDS() {
            mFormat = new DecimalFormat("###,###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(value) + " $";
        }
    }
