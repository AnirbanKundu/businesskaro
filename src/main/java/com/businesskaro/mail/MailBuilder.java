package com.businesskaro.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

public class MailBuilder {

	public StringBuffer process(MailEnum mailEnum, Map<String,String> params ){
		StringBuffer mailTemplate;
		try {
			mailTemplate = loadEmailTemplate(mailEnum.getTemplateName());
			for(Entry<String,String> entry :  params.entrySet()){
				String key = entry.getKey();
				String val = entry.getValue();
				int index = -1;
				while ( (index = mailTemplate.indexOf(key)) != -1){
					mailTemplate.replace(index, index + key.length(), val);
				}
			}
			 return mailTemplate;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private StringBuffer loadEmailTemplate(String templateName) throws IOException {
		InputStream ios = MailBuilder.class.getClassLoader().getResourceAsStream(templateName + ".txt");
		BufferedReader reader = new BufferedReader( new InputStreamReader(ios));
		StringBuffer buffer = new StringBuffer();
		String line = null;
		while( (line = reader.readLine()) != null){
			buffer.append(line);
		}
		return buffer;
	}
}
