package com.example.simplesaletransection.iso;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by laikey on 2016/12/28.
 */

public class IsoJson {
//    private static ResponseActivity responseActivity;
    private static final String TAG = "IsoJson";
    private static JSONArray isoJson_bitAttrFormat;

    private IsoJson() {
        isoJson_bitAttrFormat = new JSONArray();
    }

    /**
     * setBitFormat - 设置ISO8583域的属性
     *
     * @param bitAttrStr - 为JSON数据列表, 如下格式：[{"name":"F01","type":"BCD","len":8},{"name":"F02","type":"LBCD","len":19}]
     *                   name:域的名称, 比如：F1表示第1域,F64为第64域
     *                   type:域的数据类型, 取值范围：ASC, LASC, LASC, LLLASC, BCD, LBCD, LLBCD, LLLBCD:
     *                   ASC为ASC码类型, BCD为压缩的BCD码类型，前面一个L表示以为长度为一变成BCD码长度
     *                   len: 域是固定数据长度,或者域的可变长的最大长度
     */
    public static boolean setBitFormat(String bitAttrStr) {
        //Log.i(TAG, "setBitFormat jsonFormat = " + bitAttrStr);
        JSONArray bitAttr;
        try {
            bitAttr = new JSONArray( bitAttrStr );
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i( TAG, "convert string to JSONArray fail!" );
            return false;
        }

        isoJson_bitAttrFormat = bitAttr;
        return true;
    }

    /**
     * clearAll - 清空JSON数据格式里头的全部域数据
     *
     * @param jsonData
     */
    public static void clearAll(JSONObject jsonData) {
        //Log.i(TAG, "clearAll() executed");
        jsonData = null;
    }

    /**
     * cearBit - 清除JSON数据格式里头的某个域是数据
     *
     * @param jsonData - JSON数据格式
     * @param bit      - 域名称
     * @return - 失败返回false, 成功返回true
     */
    public static boolean clearBit(JSONObject jsonData, int bit) {
        Log.i( TAG, "clearBit() executed, bit=" + bit );
        if (jsonData != null) {
            String bitName = String.format( "F%d", bit );
            jsonData.remove( bitName );
            return true;
        }
        return false;
    }

    /**
     * setBit - 给JSON数据格式设置某个域的数据
     *
     * @param jsonData - JSON数据格式
     * @param bit      - 域名称
     * @param bitData  - 需要设的域数据, ASC格式数据
     * @return - 失败返回false, 成功返回true
     */
    public static boolean setBit(JSONObject jsonData, int bit, String bitData) {
        Log.i( TAG, "setBit() executed, bit=" + bit + ",data=" + bitData );
        if (bitData == null || bitData.length() <= 0) {
            return false;
        }
        if (jsonData != null) {
            try {
                String bitName = String.format( "F%d", bit );
                jsonData.put( bitName, bitData );
                //Log.i(TAG, "set bit success");
                return true;
            } catch (JSONException e) {
            }
        }
        Log.i( TAG, "set bit fail" );
        return false;
    }

    /**
     * setBitByte - 给JSON数据格式设置某个域的数据
     *
     * @param jsonData - JSON数据格式
     * @param bit      - 域名称
     * @param bitData  - 需要设的域数据, ASC格式数据
     * @return - 失败返回false, 成功返回true
     */
    public static boolean setBitByte(JSONObject jsonData, int bit, String bitData) {
        Log.i( TAG, "setBitByte() executed, bit=" + bit + ",data=" + bitData );
        if (jsonData != null) {
            try {
                String data = "[{B}]" + bitData;
                String bitName = String.format( "F%d", bit );
                jsonData.put( bitName, data );
//               Log.i(TAG, "set bit success");
                return true;
            } catch (JSONException e) {
            }
        }
        Log.i( TAG, "set bit fail" );
        return false;
    }

    /**
     * getField - 从JSON数据格式中获取某个域的数据
     *
     * @param jsonData - JSON数据格式
     * @param bit      - 域名称
     * @return - 返回域数据, ASC格式的数据, 失败返回null
     */
    public static String getField(JSONObject jsonData, int bit) {
        Log.i(TAG, "GetBit() executed, bit=" + bit);
        if (jsonData != null) {
            String str;
            String bitName = String.format( "F%d", bit );
            if (!jsonData.isNull( bitName )) {
                try {
                    str = jsonData.getString( bitName );
                    Log.i( TAG, String.format( "[F%03d]", bit ) + " [" + str + "]" );
                    return getDataByBitAttrType( bit, str );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * getField - 从JSON数据格式中获取某个域的数据
     *
     * @param jsonData - JSON数据格式
     * @param bit      - 域名称
     * @return - 返回域数据, ASC格式的数据, 失败返回null
     */
    public static byte[] getBitByte(JSONObject jsonData, int bit) {
        //Log.i(TAG, "GetBitByte() executed, bit=" + bit);
        if (jsonData != null) {
            String str;
            String bitName = String.format( "F%d", bit );
            if (!jsonData.isNull( bitName )) {
                try {
                    str = jsonData.getString( bitName );
                    Log.i( TAG, "get bit(" + bit + ") data=" + str );
                    if (str == null || str.length() <= 0) {
                        return null;
                    } else {
                        if ((str.length() > 5) && ("[{A}]".equals( str.substring( 0, 5 ) )))
                            return Utils.asc2Bcd( str.substring( 5 ) );
                        else
                            return Utils.asc2Bcd( str );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * json2IsoData: 把JSON数据转化为ISO8583数据格式的数据
     *
     * @param jsonData - JSON数据
     * @return - ISO8583数据格式的数据, 返回ASC码格式数据, 失败返回null
     */
    public static String json2IsoData(JSONObject jsonData) {
        Log.i(TAG, "json2IsoData jsonData=" + jsonData.toString());

        int i;
        int bitFld;
        int bitNum = 8;
        String bitMap = "";
        String bitValue;
        String isoData = "";
        String tmpFieldData;
        String msgId = "";
        JSONObject jsonBit;

        try {
            bitFld = 128;
            if (!isoJson_bitAttrFormat.isNull( bitFld )) {
                bitNum = 16;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.i(TAG, "bitNum=" + bitNum);

        for (bitFld = 0; bitFld <= bitNum * 8; bitFld++) {
            //get bit field data
            //bitValue = getBitDataWithType(jsonData, bitFld);
            bitValue = getField( jsonData, bitFld );
            if (bitValue == null) {
                //Log.i(TAG, "get bit(field=" + bitFld + ") data fail");
                bitMap += "0";
                continue;
            }
//            ascBcdLenMode = false;
//            if( bitValue.length() > 5 && "[{B}]".equals(bitValue.substring(0, 5))){
//                ascBcdLenMode = true;
//                bitValue = bitValue.substring(5);
//            }

            //  set bitfield data
            // get field bit format attribute
            try {
                try {
                    jsonBit = isoJson_bitAttrFormat.getJSONObject( bitFld );
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Log.i( TAG, "iso json_bitAttrFormat.get(" + bitFld + ") cause exception=" + e1.getMessage() );
                    continue;
                }

                if (jsonBit == null) {
                    Log.i( TAG, "get iso json_bitAttrFormat.get(" + bitFld + ") fail" );
                    continue;
                }
                //Log.i(TAG, "bit(" + bitFld + ") format=" + jsonBit.toString());

                // jsonBit data is:{"name":"F2","type":"LBCD","len":19}
//                if(String.format("F%d", bitFld).equals(jsonBit.optString("name").toString())){
//                    Log.i( TAG, "bit format name=" + jsonBit.optString("name").toString() + " is not " + String.format("F%d", bitFld) + ",fail!" );
//                    continue;
//                }

                //get bit type
                String bitType = jsonBit.optString( "type" );
                if (bitType == null) {
                    Log.i( TAG, "jsonBit(" + bitFld + ") type is null" );
                    continue;
                }

                //get bit length
                int dataLength = bitValue.length();
                int bitLength = jsonBit.optInt( "len" );

                if ("L".equals( bitType.substring( 0, 1 ) )) {
                    // variable length mode
                    String str;
                    for (i = 0; i < 5; i++) {
                        str = bitType.substring( i, i + 1 );
                        if (!"L".equals( str )) {
                            break;
                        }
                    }

                    if (dataLength > bitLength) {
                        dataLength = bitLength;
                    }

                    if (bitType.contains( "BIN" )) {
                        tmpFieldData = Utils.byte2HexStr( Utils.int2Bcd( dataLength / 2, i ) );
//                        Log.i(TAG, "Llen org len=" + dataLength + ",(bcd len)=" + Utils.byte2HexStr(Utils.int2Bcd(dataLength / 2, i)));
                    } else {
                        tmpFieldData = Utils.byte2HexStr( Utils.int2Bcd( dataLength, i ) );
                        //Log.i(TAG, "Llen=" + Utils.byte2HexStr(Utils.int2Bcd(dataLength, i)));
                    }

                    if (bitType.contains( "BCD" ) || bitType.contains( "BIN" )) {
                        //对变长域的处理调用Utils.asc2BcdLeft()以左对齐。
                        tmpFieldData += Utils.byte2HexStr( Utils.asc2BcdLeft( bitValue, dataLength ) );
//                        Log.i(TAG, "bcd var data=" + Utils.byte2HexStr(Utils.asc2Bcd(bitValue, dataLength)));
                    } else {
                        tmpFieldData += Utils.str2Asc( bitValue.substring( 0, dataLength ) );
//                        Log.i(TAG, "asc var data=" + Utils.str2Asc(bitValue.substring(0, dataLength)));
                    }
                } else {
                    // fix length mode
                    int rlen = bitLength;
                    if (bitType.contains( "BIN" )) {
                        rlen *= 2;
                    }
                    if (rlen < bitValue.length()) {
                        bitValue = bitValue.substring( 0, rlen );
                    } else if (rlen > bitValue.length()) {
                        if (bitType.contains( "BCD" )) {
                            bitValue = Utils.addPadding( bitValue, true, '0', rlen );
                        } else {
                            bitValue = Utils.addPadding( bitValue, false, ' ', rlen );
                        }
                    }

                    if (bitType.contains( "BCD" )) {
//                        if (bitFld != 22) {
                        tmpFieldData = Utils.byte2HexStr( Utils.asc2Bcd( bitValue ) );
//                        } else {
//                            tmpFieldData = Utils.byte2HexStr( Utils.asc2BcdLeft( bitValue, rlen ) );
//                        }
//                      Log.i(TAG, "bcd fix data=" + Utils.byte2HexStr(Utils.asc2Bcd(bitValue)));
                    } else if (bitType.contains( "BIN" )) {
                        tmpFieldData = bitValue;
//                      Log.i(TAG, "bin fix data=" + bitValue);
                    } else {
                        tmpFieldData = Utils.str2Asc( bitValue );
//                      Log.i(TAG, "asc fix data=" + Utils.str2Asc(bitValue));
                    }
                }

                //set bitmap and add field data
                if (bitFld == 0) {
                    msgId = tmpFieldData;
                } else {
                    isoData += tmpFieldData;
                    bitMap += "1";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }// for

        tmpFieldData = isoData;
        isoData = msgId + Utils.bcd2Asc( Utils.bit2HexByte( bitMap, bitMap.length() ) ) + tmpFieldData;

        //Log.i(TAG, "isoData=" + isoData);
        return isoData;
    }

    /**
     * isoData2Json: 把iso8583包asc数据转化为JSON格式存储
     *
     * @param isoData - iso8583包数据, 为asc格式数据
     * @return - 返回 JSON数据, 失败返回null
     */
    public static JSONObject isoData2Json(String isoData) {
//        ResponseEntity responseEntity;
        //Log.i(TAG, "isoData2Json isoData=" + isoData);

        int bitNum = 8;
        int bitFld;
        int offset;
        byte[] bitMap = new byte[16];
        byte[] bcdIsoData = Utils.asc2Bcd( isoData );
        JSONObject jsonRet = new JSONObject();

        clearAll( jsonRet );
        //Log.i(TAG, "bcdIsoData Length =" + bcdIsoData.length);
        System.arraycopy( bcdIsoData, 2, bitMap, 0, 8 );
        if ((bitMap[0] & 0x80) == 0x80) {
            bitNum = 16;
            System.arraycopy( bcdIsoData, 2 + 8, bitMap, 8, 8 );
        }
        //Log.i(TAG, "bitmap(" + bitNum + ")=" + Utils.bcd2Asc(bitMap, bitNum));

        //get msgid
        String tmpFieldStr = isoData.substring( 0, 4 );
        bitFld = 0;
        String fieldName = String.format( "F%d", bitFld );
        try {
            jsonRet.put( fieldName, tmpFieldStr );
            Log.i( TAG,">>>" + fieldName +":"+tmpFieldStr );
//            Log.i( TAG, String.format( "[F%03d]", bitFld ) + " = " + tmpFieldStr );
            //Log.i(TAG, "jsonRet put field=" + fieldName + ", string =" + tmpFieldStr);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i( TAG, "put bit(field=" + bitFld + ") JSON data fail" );
            return null;
        }

        offset = 4 + bitNum * 2;
        Bundle bdl = new Bundle();
        for (int k = 0; k < 8; k++) {
            String bitStr = Integer.toBinaryString( (bitMap[k] & 0xFF) + 0x100 ).substring( 1 );
            for (int i = 0; i < 8; i++) {
//                responseEntity=new ResponseEntity();
                bitFld = k * 8 + i + 1;
                if ("0".equals( bitStr.substring( i, i + 1 ) )) {
                    continue;
                }
                //Log.i(TAG, "iso bit field=" + bitFld);

                tmpFieldStr = isoData.substring( offset );
                bdl = getBitData( bitFld, tmpFieldStr );
                if (bdl == null) {
                    Log.i( TAG, "get bit(field=" + bitFld + ") data fail" );
                    continue;
                }
                offset += bdl.getInt( "len" );
                //Log.i(TAG, "jsonRet put offset =" + offset + " len " + bdl.getInt("len"));

                fieldName = String.format( "F%d", bitFld );
                try {
                    //Log.i(TAG, "jsonRet put field=" + fieldName + ", string=" + bdl.getString("value"));
                    jsonRet.put( fieldName, bdl.getString( "value" ) );
 //                   Log.i( TAG,">>>" + fieldName +":"+bdl.getString( "value" ));
 //                   responseEntity.setFieldName(fieldName);
 //                   responseEntity.setValues(bdl.getString( "value" ));
 //                   responseActivity.saved(responseEntity);
                    Log.i( TAG,">>>" + fieldName +":"+bdl.getString( "value" ));

//                    Log.i( TAG, String.format( "[F%03d]", bitFld ) + " = " + getDataByBitAttrType( bitFld, bdl.getString( "value" ) ) );
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( TAG, "put bit(field=" + bitFld + ") JSON data fail" );
                }
            }
        }
        //Log.i(TAG, "jsonRet =" + jsonRet.toString());
        return jsonRet;
    }

    /**
     * isoData2Json: 把iso8583包asc数据转化为JSON格式存储
     *
     * @param isoData - iso8583包数据, 为asc格式数据
     * @return - 返回 JSON数据, 失败返回null
     */
/*    public static JSONObject isoData2Json(Iso8583TransData transData_Recv, String isoData) {
        //Log.i(TAG, "isoData2Json isoData=" + isoData);

        int bitNum = 8;
        int bitFld;
        int offset;
        byte[] bitMap = new byte[16];
        byte[] bcdIsoData = Utils.asc2Bcd( isoData );
        JSONObject jsonRet = new JSONObject();

        clearAll( jsonRet );
        //Log.i(TAG, "bcdIsoData Length =" + bcdIsoData.length);
        System.arraycopy( bcdIsoData, 2, bitMap, 0, 8 );
        if ((bitMap[0] & 0x80) == 0x80) {
            bitNum = 16;
            System.arraycopy( bcdIsoData, 2 + 8, bitMap, 8, 8 );
        }
        //Log.i(TAG, "bitmap(" + bitNum + ")=" + Utils.bcd2Asc(bitMap, bitNum));

        //get msgid
        String tmpFieldStr = isoData.substring( 0, 4 );
        bitFld = 0;
        String fieldName = String.format( "F%d", bitFld );
        try {
            jsonRet.put( fieldName, tmpFieldStr );
            transData_Recv.byBitMapStoreData( bitFld, tmpFieldStr );
            //Log.i(TAG, " jsonRet put field=" + fieldName + ", string =" + tmpFieldStr);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i( TAG, "put bit(field=" + bitFld + ") JSON data fail" );
            return null;
        }

        offset = 4 + bitNum * 2;
        Bundle bdl = new Bundle();
        for (int k = 0; k < 8; k++) {
            String bitStr = Integer.toBinaryString( (bitMap[k] & 0xFF) + 0x100 ).substring( 1 );
            for (int i = 0; i < 8; i++) {
                bitFld = k * 8 + i + 1;
                if ("0".equals( bitStr.substring( i, i + 1 ) )) {
                    continue;
                }
                //Log.i(TAG, "iso bit field=" + bitFld);

                tmpFieldStr = isoData.substring( offset );
                bdl = getBitData( bitFld, tmpFieldStr );
                if (bdl == null) {
                    Log.i( TAG, "get bit(field=" + bitFld + ") data fail" );
                    continue;
                }
                offset += bdl.getInt( "len" );
                //Log.i(TAG, "jsonRet put offset =" + offset + " len " + bdl.getInt("len"));

                fieldName = String.format( "F%d", bitFld );
                try {
                    //Log.i(TAG, "jsonRet put field=" + fieldName + ", string=" + bdl.getString("value"));
                    jsonRet.put( fieldName, bdl.getString( "value" ) );
                    transData_Recv.byBitMapStoreData( bitFld, bdl.getString( "value" ) );
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( TAG, "put bit(field=" + bitFld + ") JSON data fail" );
                }
            }
        }
        //Log.i(TAG, "jsonRet =" + jsonRet.toString());
        return jsonRet;
    }*/

    /**
     * isoRealData2Json: 把iso8583包asc数据转化为JSON格式存储
     * fx：用getBitRealData 取域值
     *
     * @param isoData - iso8583包数据, 为asc格式数据
     * @return - 返回 JSON数据, 失败返回null
     */

    public static JSONObject isoRealData2Json(String isoData) {
        Log.i( TAG, "isoRealData2Json isoData=" + isoData );

        int bitNum = 8;
        int bitFld;
        int offset;
        byte[] bitMap = new byte[16];
        byte[] bcdIsoData = Utils.asc2Bcd( isoData );
        JSONObject jsonRet = new JSONObject();

        clearAll( jsonRet );

        System.arraycopy( bcdIsoData, 2, bitMap, 0, 8 );
        if ((bitMap[0] & 0x80) == 0x80) {
            bitNum = 16;
            System.arraycopy( bcdIsoData, 2 + 8, bitMap, 8, 8 );
        }
        Log.i( TAG, "bitmap(" + bitNum + ")=" + Utils.bcd2Asc( bitMap, bitNum ) );

        //get msgid
        String tmpFieldStr = isoData.substring( 0, 4 );
        bitFld = 0;
        String fieldName = String.format( "F%d", bitFld );
        try {
            jsonRet.put( fieldName, tmpFieldStr );
            Log.i( TAG, " jsonRet put field=" + fieldName + ", string=" + tmpFieldStr );
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i( TAG, "put bit(field=" + bitFld + ") JSON data fail" );
            return null;
        }

        offset = 4 + bitNum * 2;
        Bundle bdl = new Bundle();
        for (int k = 0; k < 8; k++) {
            String bitStr = Integer.toBinaryString( (bitMap[k] & 0xFF) + 0x100 ).substring( 1 );
            for (int i = 0; i < 8; i++) {
                bitFld = k * 8 + i + 1;
                if ("0".equals( bitStr.substring( i, i + 1 ) )) {
                    continue;
                }
                Log.i( TAG, "iso bit field=" + bitFld );

                tmpFieldStr = isoData.substring( offset );
                bdl = getBitRealData( bitFld, tmpFieldStr );
                if (bdl == null) {
                    Log.i( TAG, "get bit(field=" + bitFld + ") data fail" );
                    continue;
                }
                offset += bdl.getInt( "len" );

                fieldName = String.format( "F%d", bitFld );
                try {
                    Log.i( TAG, "jsonRet put field=" + fieldName + ", string=" + bdl.getString( "value" ) );

                    jsonRet.put( fieldName, bdl.getString( "value" ) );
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i( TAG, "put bit(field=" + bitFld + ") JSON data fail" );
                }
            }
        }
        return jsonRet;
    }

    /**
     * getBitAttrType-返回域是属性类型
     *
     * @param bitFld
     * @return-ASC码还是BCD码
     */
    public static String getBitAttrType(int bitFld) {
        JSONObject jsonBit;

        // get field bit format attribute
        try {
            jsonBit = isoJson_bitAttrFormat.getJSONObject( bitFld );
        } catch (JSONException e1) {
            e1.printStackTrace();
            Log.i( TAG, "isoJson_bitAttrFormat.get(" + bitFld + ") cause exception=" + e1.getMessage() );
            return "";
        }

        if (jsonBit == null) {
            Log.i( TAG, "get isoJson_bitAttrFormat.get(" + bitFld + ") fail" );
            return "";
        }

        return jsonBit.optString( "type" );
    }

    /**
     * getBitData: 取某个域的数据
     *
     * @param bit     - field no
     * @param isoData - iso data
     * @return -  data gotten
     */
    public static Bundle getBitData(int bit, String isoData) {
        //Log.i(TAG, "getBitData iso bit=" + bit  + "data=" + isoData);

        int i;
        JSONObject jsonBit;
        String bitName = String.format( "F%d", bit );
        String retFieldData;

        // get field bit format attribute
        try {
            /**
             *  bitAttrFormat:[{"name":"F01","type":"BCD","len":8},{"name":"F02","type":"LBCD","len":19}]
             */
            try {
                jsonBit = isoJson_bitAttrFormat.getJSONObject( bit );
            } catch (JSONException e1) {
                e1.printStackTrace();
                Log.i( TAG, "iso json_bitAttrFormat.get(" + bit + ") cause exception=" + e1.getMessage() );
                return null;
            }

            if (jsonBit == null) {
                Log.i( TAG, "get iso json_bitAttrFormat.get(" + bit + ") fail" );
                return null;
            }
//          Log.i(TAG, "bit(" + bit + ") format=" + jsonBit.toString());

            //jsonBit data is:{"name":"F2","type":"LBCD","len":19}
//            if(bitName.equals(jsonBit.optString("name")) ){
//                Log.i( TAG, "bit format name=" + jsonBit.optString("name") + "is not " + bitName + ",fail!" );
//                return 0;
//            }

            // get bit type
            String bitType = jsonBit.optString( "type" );
            if (bitType == null) {
                Log.i( TAG, "jsonBit(" + bit + ") type is null" );
                return null;
            }

            // get bit length
            int offset;
            int dataLength;
            int bitLength = jsonBit.optInt( "len" );

            if ("L".equals( bitType.substring( 0, 1 ) )) {
                // variable length mode
                String str;
                for (i = 0; i < 5; i++) {
                    str = bitType.substring( i, i + 1 );
                    if (!"L".equals( str )) {
                        break;
                    }
                }
                offset = i * 2;
                byte[] lenByte;
                lenByte = Utils.asc2Bcd( isoData.substring( 0, offset ) );
                dataLength = Utils.bcd2Int( lenByte );
                //Log.i(TAG, "field[" + bit + "]  data length is " + dataLength);
                if (dataLength > bitLength) {
                    Log.i( TAG, "field[" + bit + "]  data length is invalid(dataLength=" + dataLength + ",bitLength=" + bitLength );
                    return null;
                }
//              Log.i(TAG, "field[" + bit + "]  data length=" + dataLength);
            } else {
                offset = 0;
                dataLength = bitLength;
            }

            if (bitType.contains( "BCD" )) {
                //BCD mode
                dataLength = ((dataLength % 2) != 0) ? dataLength + 1 : dataLength;

                if (isoData.length() + offset < dataLength) {
                    Log.i( TAG, "in data length(=" + isoData.length() + ") is less than bcd dataLength(=" + dataLength + ")" );
                    return null;
                }
                retFieldData = isoData.substring( offset, offset + dataLength );
//              Log.i(TAG, "getBitData() is success, field[" + bit + "]  data length=" + dataLength + ", bcd data=" + retFieldData);
            } else if (bitType.contains( "BIN" )) {
                // BIN mode
                dataLength *= 2;
                if (isoData.length() + offset < dataLength) {
                    Log.i( TAG, "in data length(=" + isoData.length() + ") is less than bcd dataLength(=" + dataLength + ")" );
                    return null;
                }
                retFieldData = isoData.substring( offset, offset + dataLength );
//              Log.i(TAG, "getBitData() is success, field[" + bit + "]  data length=" + dataLength + ", bcd data=" + retFieldData);
            } else {
                //ASC mode
                dataLength *= 2;
                if (isoData.length() + offset < dataLength) {
                    Log.i( TAG, "in data length(=" + isoData.length() + ") is less than asc dataLength(=" + dataLength + ")" );
                    return null;
                }
                //retFieldData = new String(Utils.asc2Bcd(isoData.substring(offset, offset+dataLength), dataLength));
                retFieldData = "[{A}]" + isoData.substring( offset, offset + dataLength );
//              Log.i(TAG, "getBitData() is success, field[" + bit + "]  data length=" + dataLength + ", asc data=" + retFieldData);
            }

            Bundle bdl = new Bundle();
            bdl.putInt( "len", dataLength + offset );
            bdl.putString( "value", retFieldData );
            return bdl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getBitRealData: 取某个域的数据
     * fx 根据"定长域右对齐，变长域左对齐"的对齐方式获取8583域中真实数据。
     *
     * @param bit     - field no
     * @param isoData - iso data
     * @return -  data gotten
     */
    private static Bundle getBitRealData(int bit, String isoData) {
        Log.i( TAG, "getBitRealData() executed, bit=" + bit + "   data=" + isoData );

        int i;
        JSONObject jsonBit;
        String bitName = String.format( "F%d", bit );
        String retFieldData;

        //get field bit format attribute
        try {
            /**
             *  bitAttrFormat:[{"name":"F01","type":"BCD","len":8},{"name":"F02","type":"LBCD","len":19}]
             */
            try {
                jsonBit = isoJson_bitAttrFormat.getJSONObject( bit );
            } catch (JSONException e1) {
                e1.printStackTrace();
                Log.i( TAG, "iso json_bitAttrFormat.get(" + bit + ") cause exception=" + e1.getMessage() );
                return null;
            }

            if (jsonBit == null) {
                Log.i( TAG, "get iso json_bitAttrFormat.get(" + bit + ") fail" );
                return null;
            }
//            Log.i(TAG, "bit(" + bit + ") format=" + jsonBit.toString());

            // jsonBit data is:{"name":"F2","type":"LBCD","len":19}
//            if(bitName.equals(jsonBit.optString("name")) ){
//                Log.i( TAG, "bit format name=" + jsonBit.optString("name") + "is not " + bitName + ",fail!" );
//                return 0;
//            }

            //get bit type
            String bitType = jsonBit.optString( "type" );
            if (bitType == null) {
                Log.i( TAG, "jsonBit(" + bit + ") type is null" );
                return null;
            }

            //get bit length
            int offset;
            int dataLength;
            int bitLength = jsonBit.optInt( "len" );

            if ("L".equals( bitType.substring( 0, 1 ) )) {
                // variable length mode
                String str;
                for (i = 0; i < 5; i++) {
                    str = bitType.substring( i, i + 1 );
                    if (!"L".equals( str )) {
                        break;
                    }
                }
                offset = i * 2;
                byte[] lenByte;
                lenByte = Utils.asc2Bcd( isoData.substring( 0, offset ) );
                dataLength = Utils.bcd2Int( lenByte );

                if (dataLength > bitLength) {
                    Log.i( TAG, "field[" + bit + "]  data length is invalid(dataLength=" + dataLength + ",bitLength=" + bitLength );
                    return null;
                }
//              Log.i(TAG, "field[" + bit + "]  data length=" + dataLength);
            } else {
                offset = 0;
                dataLength = bitLength;
            }

            if (bitType.contains( "BCD" )) {
                //BCD mode
                //dataLength = ((dataLength % 2) != 0) ? dataLength + 1 : dataLength;

                if (isoData.length() + offset < dataLength) {
                    Log.i( TAG, "in data length(=" + isoData.length() + ") is less than bcd dataLength(=" + dataLength + ")" );
                    return null;
                }
                // fx 取出真实的数据
                if ("L".equals( bitType.substring( 0, 1 ) )) {
                    retFieldData = isoData.substring( offset, offset + dataLength ); // 左对齐
                    offset = offset + dataLength % 2;
                } else {
                    offset = offset + dataLength % 2;
                    retFieldData = isoData.substring( offset, offset + dataLength ); //右对齐
                }
//                Log.i(TAG, "getBitData() is success, field[" + bit + "]  data length=" + dataLength + ", bcd data=" + retFieldData);
            } else if (bitType.contains( "BIN" )) {
                // BIN mode
                dataLength *= 2;
                if (isoData.length() + offset < dataLength) {
                    Log.i( TAG, "in data length(=" + isoData.length() + ") is less than bcd dataLength(=" + dataLength + ")" );
                    return null;
                }
                retFieldData = isoData.substring( offset, offset + dataLength );
//                Log.i(TAG, "getBitData() is success, field[" + bit + "]  data length=" + dataLength + ", bcd data=" + retFieldData);
            } else {
                // ASC mode
                dataLength *= 2;
                if (isoData.length() + offset < dataLength) {
                    Log.i( TAG, "in data length(=" + isoData.length() + ") is less than asc dataLength(=" + dataLength + ")" );
                    return null;
                }
                //retFieldData = new String(Utils.asc2Bcd(isoData.substring(offset, offset+dataLength), dataLength));
                retFieldData = "[{A}]" + isoData.substring( offset, offset + dataLength );
//                Log.i(TAG, "getBitData() is success, field[" + bit + "]  data length=" + dataLength + ", asc data=" + retFieldData);
            }

            Bundle bdl = new Bundle();
            bdl.putInt( "len", dataLength + offset );
            bdl.putString( "value", retFieldData );
            return bdl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getField - 从JSON数据格式中获取某个域的数据
     *
     * @param jsonData - JSON数据格式
     * @param bit      - 域名称
     * @return - 返回域数据, ASC格式的数据, 失败返回null
     */
    public static String getBitDataWithType(JSONObject jsonData, int bit) {
        //Log.i(TAG, "GetBit() executed, bit=" + bit);
        if (jsonData != null) {
            String str;
            String bitName = String.format( "F%d", bit );
            if (!jsonData.isNull( bitName )) {
                try {
                    str = jsonData.getString( bitName );
                    Log.i( TAG, "get bit(" + bit + ") data=" + str );
                    if (str == null || str.length() <= 0) {
                        return null;
                    } else {
                        if ((str.length() > 5) && ("[{A}]".equals( str.substring( 0, 5 ) )))
                            return new String( Utils.asc2Bcd( str.substring( 5 ) ) );
                        else
                            return str;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 按获取的域的自定义数据，获取真实数据
     *
     * @param str
     */
    public static String getDataByBitAttrType(int bit, String str) {
        String fieldData = null;
        String BitAttrType = IsoJson.getBitAttrType( bit );
        if ((str.length() > 5) && ("[{A}]".equals( str.substring( 0, 5 ) ))) {
            if (BitAttrType.contains( "L" ) && bit == 62) {
                fieldData = str.substring( 5 );
            } else {
                //Fix 63域中文乱码 add by Tony @20180313
                try {
                    fieldData = new String( Utils.asc2Bcd( str.substring( 5 ) ), "GB2312" );
                } catch (UnsupportedEncodingException e) {
                    Log.i( TAG, "getFieldDataByBitAttrType error" );
                    e.printStackTrace();
                }
            }
        } else if ((str.length() > 5) && ("[{B}]".equals( str.substring( 0, 5 ) ))) {
            fieldData = str.substring( 5 );
        } else {
            fieldData = str;
        }
        //Log.i( TAG, String.format( "[F%03d]", bit ) + " [" + fieldData + "]" );
        return fieldData;
    }

    /**
     * getField - 从JSON数据格式中获取某个域的数据
     *
     * @param jsonData - JSON数据格式
     * @param bit      - 域名称
     * @return - 返回域数据
     */
    public static String getBitData(JSONObject jsonData, int bit) {
        //Log.i(TAG, "GetBit() executed, bit=" + bit);
        if (jsonData != null) {
            String str;
            String bitName = String.format( "F%d", bit );
            if (!jsonData.isNull( bitName )) {
                try {
                    str = jsonData.getString( bitName );
                    if (str == null || str.length() <= 0) {
                        return null;
                    } else {
                        if ((str.length() > 5) && ("[{A}]".equals( str.substring( 0, 5 ) )))
                            return str.substring( 5 );
                        else
                            return str;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
