package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Adel Ferdjoukh
 *
 */
public class ClassInstance {
	
	private String oid;
	private String type;
	private ArrayList<Attribute> attrs;
	private ArrayList<Link> links;
	
	public ClassInstance(String oid,String type){
		this.oid=oid;
		this.type=type;
		attrs=new ArrayList<Attribute>();
		links=new ArrayList<Link>();
	}
	
	public ClassInstance(String oid,String type, ArrayList<Attribute> attrs, ArrayList<Link> links){
		this.oid=oid;
		this.type=type;
		this.attrs=attrs;
		this.links=links;
	}

	public ArrayList<Attribute> getAttrs() {
		return attrs;
	}

	public void setAttrs(ArrayList<Attribute> attrs) {
		this.attrs = attrs;
	}

	public ArrayList<Link> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}

	public String getOid() {
		return oid;
	}

	public String getType() {
		return type;
	}
	
	public void addAttribute(Attribute a){
		this.attrs.add(a);
	}
	
	public void modifyAttribute(String name,String newVal){
		for(Attribute a: this.attrs){
			if(a.getName().equals(name)){
				a.setValue(newVal);
			}
		}
	}
	
	public void addLink(Link l){
		this.links.add(l);
	}
	
	public static String randomOID(){
		Random rand= new Random();
		String res="";
		int nb= rand.nextInt(3)+3;
		for(int i=0;i<nb-1;i++){
			int a= rand.nextInt(26);
			char c= (char) (a+97);
			res+=c;
		}
		return res;
	}
	
}
