import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.*;
import Ecore.*;

/**
 * 
 * @author Adel Ferdjoukh
 *
 */
public class VERIATLFileReader {

	private String veriAtlFile;
	private String ecoreFile;
	private String rootClass;
	private Model model;
	private EcoreFileReader ecoreReader;
	
	public VERIATLFileReader(String atlfile, String ecorefile, String root){
		this.veriAtlFile=atlfile;
		this.ecoreFile=ecorefile;
		this.rootClass=root;
		this.ecoreReader= new EcoreFileReader(ecoreFile, rootClass);
	}

	public Model getModel() {
		return model;
	} 

	public void generateModel() throws IOException{
		model=new Model();
		
		File file= new File(veriAtlFile);
		FileInputStream fis= new FileInputStream(file);
		BufferedInputStream bis= new BufferedInputStream(fis);
		
		DataInputStream reader = new DataInputStream(bis);
		
		String line="";
		
		while(reader.available()!=0)
		{
			line=reader.readLine();
			//Ignore comments (starting with #)
			if (!line.startsWith("#") && !line.equals(""))
			{
				//Create All Objects
				if(line.startsWith("Object")){
					String ins[]= new String[2];
					Pattern p = Pattern.compile(" [a-zA-Z0-9]*");
					Matcher m= p.matcher(line);
					int i=0;
					while (m.find())
					{
						ins[i]=m.group().replace(" ", "");	
						i++;
					}
					//Create one new object
					ClassInstance c= new ClassInstance(ins[0], ins[1]);
					model.add(c);
				}else if(line.startsWith("Attribute")){
					//Set attribute values
					String ins = line.split("Attribute ")[1];
					
					if(ins.indexOf('.')==ins.lastIndexOf('.')){
						String oid= ins.substring(0,ins.indexOf('.'));
						String attr= ins.substring(ins.indexOf('.')+1,ins.indexOf('='));
						String value= ins.substring(ins.indexOf('=')+1,ins.length());
						
						Attribute a= new Attribute(attr, value);
						model.find(oid).addAttribute(a);
					}else
					{
						String oid= ins.substring(0,ins.indexOf('.'));
						String attr= ins.substring(ins.indexOf('.')+1,ins.indexOf('='));
						String oid2= ins.substring(ins.indexOf('=')+1,ins.lastIndexOf('.'));
						String attr2= ins.substring(ins.lastIndexOf('.')+1,ins.length());
						
						String value=Attribute.createRandom();
						Attribute a= new Attribute(attr,value);
						model.find(oid).addAttribute(a);
						
						Attribute a2= new Attribute(attr,value);
						model.find(oid2).addAttribute(a2);
					}
				}else if(line.startsWith("Link")){
					//Set Links
					String ins = line.split("Link ")[1];
					if(ins.indexOf('.')==ins.lastIndexOf('.')){
						String oid= ins.substring(0,ins.indexOf('.'));
						String role= ins.substring(ins.indexOf('.')+1,ins.indexOf('='));
						String target= ins.substring(ins.indexOf('=')+1,ins.length());
						//System.out.println(oid+ " "+ role+" is linked to "+target);
						
						ClassInstance ci= model.find(target);
						Link link= new Link(role, ci.getType(), ci);
						model.find(oid).addLink(link);
						
					}else
					{
						String oid= ins.substring(0,ins.indexOf('.'));
						String role= ins.substring(ins.indexOf('.')+1,ins.indexOf('='));
						String oid2= ins.substring(ins.indexOf('=')+1,ins.lastIndexOf('.'));
						String role2= ins.substring(ins.lastIndexOf('.')+1,ins.length());
						
						//System.out.println(oid+ " "+ role+" is linked to "+oid2+" "+role2);
					
						String type= ecoreReader.getReferenceByName(model.find(oid).getType(), role).getEReferenceType().getName();
						ClassInstance target= new ClassInstance(ClassInstance.randomOID(), type);
						model.add(target);
						Link link= new Link(role, target.getType(), target);
						model.find(oid).addLink(link);
						Link link2= new Link(role2, target.getType(), target);
						model.find(oid2).addLink(link2);
					}
				}
			}
		}
	}
	
	public Attribute createAttribute(){
		Attribute a=null;
		
		return a;
	}
	
	public Link createLink(){
		
		return null;
	}
	
}
