package com.example.rediswsclient;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Util {
	
	public static String makeStackTrace(Throwable t){
		if(t == null) return "";
		try{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			t.printStackTrace(new PrintStream(bout));
			bout.flush();
			String error = new String(bout.toByteArray());
	
			return error.replace("\r\n", " ");
		}catch(Exception ex){
		return "";
		}
	}

    /**
     * 특정token으로 split하는 Method
     * @param value
     * @param token
     * @return String[]
     */
    public static String [] split (String value, String token) {
        String[] ret = null;
        StringTokenizer st = null;
        int len = 0;
        
        value = (value==null?" ":value);
        st = new StringTokenizer(value, token);
        len = st.countTokens();
        ret = new String[len];
        
        for(int i=0;i<len;i++)
            ret[i] = st.nextToken().trim(); 
        return ret;
    }
    
    public static String removeToken( String value, String token ) {
        StringBuilder ret = new StringBuilder();
        StringTokenizer st = null;
        int len = 0;
        
        value = (value==null?" ":value);
        st = new StringTokenizer(value, token);
        len = st.countTokens();
                
        for(int i=0;i<len;i++)
            ret.append(st.nextToken().trim()); 
        return ret.toString();
    }

    public static String nvl(String str, String nullStr)
    {
        if (str == null || str.trim().length() == 0)
            return nullStr;
        else
            return str;
    }
    
    /**
     * Bean to Json String Convert
     * 
     * @param src
     * @return
     * @throws JsonProcessingException 
     */
    public static String jacksonBeanToJson(Object src)
    {
        ObjectMapper mapper  = new ObjectMapper();
        try {
            return mapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    

    public static <T> T jacksonJsonTobean( String json, Class<T> classOfT )
    {
        if( classOfT == null )
            return null;
        ObjectMapper mapper  = new ObjectMapper();
        
        try {
            return mapper.readValue(json, classOfT);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}

