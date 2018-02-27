package Model;

import java.util.Random;

/**
 * 
 * @author Adel Ferdjoukh
 *
 */
public class Attribute {
	
	private String name;
	private String value;
	
	public Attribute(String n, String v){
		name=n;
		value=v;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}
	
	public static String createRandom(){
		Random rand= new Random();
		String res="";
		int nb= rand.nextInt(5)+5;
		for(int i=0;i<nb-1;i++){
			int a= rand.nextInt(26);
			char c= (char) (a+97);
			res+=c;
		}
		return res;
		
	}
}
