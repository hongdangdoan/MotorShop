package com.example.motorshop.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class Helper {

    public Helper() {}

    public boolean isNull(String str) {
        if(str == null) return true;
        return str.trim().isEmpty();
    }

    public boolean isNullNum(Integer i){
        return ( i == null ? 0 : i) <= 0;
    }

    public boolean isNum(String str) {
        if(isNull(str)) return false;
        return str.trim().matches("\\d+");
    }

    public boolean isAlpha(String str) {
        if(isNull(str)) return false;
        return str.trim().matches("[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]+");
    }

    public boolean isAlphaAndNum(String str) {
        if(isNull(str)) return false;
        return str.trim().matches("[a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]+");
    }

    public boolean isNoSpace(String str) {
        return str.matches("\\S+");
    }

    public boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._-][a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]");
    }

    public boolean isInfoContent(String info) {
        return info.trim().matches("[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ*,.;: ]+");
    }

    public String formatNoSpace(String str) {
        if(isNull(str)) return "";
        return str.replaceAll("\\s{2,}", "").trim();
    }

    public String formatOneSpace(String str) {
        if(isNull(str)) return "";
        return str.replaceAll("\\s{2,}", " ").trim();
    }

    public String formatName(String name) {
        if(isNull(name)) return "";
        name = formatOneSpace(name);
        String[] strs = name.split(" ");
        name = "";
        for(String str : strs)
            name += str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase() + " ";
        return name.trim();
    }

    public String formatCurrency(String unfmtStr){
        if(isNull(unfmtStr)) return "";

        int mod = unfmtStr.trim().length()%3;

        String output = "";
        output += unfmtStr.substring(0,mod);
        unfmtStr = unfmtStr.substring(mod);
        int count = 0;
        while(count < unfmtStr.length()-3){
            output += "." + unfmtStr.substring(count, count+3);
            count += 3;
        }
        output += "." + unfmtStr.substring(count, unfmtStr.length());

        //xoa cac '.' du thua
        if(output.charAt(0) == '.') output = output.substring(1);
        if(output.charAt(0) == '-' && output.charAt(1) == '.') output = output.substring(0,1) + output.substring(2,output.length());
        if(output.charAt(output.length()-1) == '.') output = output.substring(0,output.length()-1);
        return output;
    }

    public String unFormatCurrency(String fmtStr){
        if(isNull(fmtStr)) return "";

        String parts[] = fmtStr.split("\\.");
        for (String part : parts) {
            fmtStr += part;
        }
        return fmtStr;
    }

    public String getDateTime(){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
        //get dd/MM/yyyy only:  str.substring(0,10)
        //get hh:mm:ss only:    str.substring(11)
    }

    public String findNextId(String maxId) {
        String model = "";
        for(int i=0; i<maxId.length(); i++){
            if(isAlpha(maxId.charAt(i) + ""))
                model += maxId.charAt(i);
        }
        String nextId = null;
        nextId = maxId.substring(model.length(), maxId.length());
        if(isNum(nextId)) {
            int nextNum = Integer.parseInt(nextId) + 1;
            if(nextNum < 10 )
                nextId = model + "0" + nextNum;
            else
                nextId = model + nextNum;
        }
        return nextId;
    }

    public String getCryptoHash(String input, String algorithm) {
        try {
            //MessageDigest classes Static getInstance method is called with MD5 hashing
            MessageDigest msgDigest = MessageDigest.getInstance(algorithm);

            //digest() method is called to calculate message digest of the input
            //digest() return array of byte.
            byte[] inputDigest = msgDigest.digest(input.getBytes());

            // Convert byte array into signum representation
            // BigInteger class is used, to convert the resultant byte array into its signum representation
            BigInteger inputDigestBigInt = new BigInteger(1, inputDigest);

            // Convert the input digest into hex value
            String hashtext = inputDigestBigInt.toString(16);

            //Add preceding 0's to pad the hashtext to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // Catch block to handle the scenarios when an unsupported message digest algorithm is provided.
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
