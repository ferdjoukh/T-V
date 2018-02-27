package Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 
 * @author Adel Ferdjoukh
 *
 */
public class Model {
	
	private ArrayList<ClassInstance> objects;
	
	public Model(){
		objects=new ArrayList<ClassInstance>();
	}

	public ArrayList<ClassInstance> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<ClassInstance> objects) {
		this.objects = objects;
	}
	
	public void add(ClassInstance o){
		this.objects.add(o);
	}
	
    /**
     * 
     * @param oid: given name of the object
     * @return: An Object or null
     * 
     */
	public ClassInstance find(String oid){
		
		ClassInstance res=null;
		for(ClassInstance ci:this.objects){
			
			if(ci.getOid().equals(oid)){
				res=ci;
			}
			
		}
		return res;	
	}
	
	@Override
	public String toString(){
		String m="Model[\n";
			
		for(ClassInstance o: objects){
			
			String attrs="";
			String links="";
			for(Attribute a:o.getAttrs()){
				attrs=attrs+a.getName()+"="+a.getValue()+" "; 
			}
			
			for(Link l:o.getLinks()){
				String oid=o.getOid();
				String role=l.getRole();
				String target=l.getTarget().getOid();
				links=links+ oid+"."+role+"="+target+" ";
			}
			
			m=m+ "("+ o.getOid()+":"+o.getType() +" "+ attrs+" "+links+")\n";
		}
		
		m=m+"]";		
		return m;
	}
	
	public void generateDotFile(String filePath) throws IOException{
		
		System.out.print("Dot file: "+ filePath+" is being created ...");
		
		String content="";
		PrintWriter ecrivain;
		
		ArrayList<String> references= new ArrayList<String>();
				
		ecrivain =  new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
		
		ecrivain.write("Graph g{ \n");
		
		for(ClassInstance ci:this.objects){
			String attrs="";
			
			for(Attribute a:ci.getAttrs()){
				attrs=attrs+a.getName()+"="+a.getValue()+"\\n"; 
			}
			ecrivain.write(ci.getOid()+" [shape=record,label=\"{"+ ci.getOid()+":"+ci.getType() +"|"+ attrs +"}\"];\n"); 
		
			for(Link l:ci.getLinks()){
				String target=l.getTarget().getOid();
				ecrivain.write(ci.getOid()+"--"+target+"[arrowhead=open,arrowtail=open,dir=forward,label="+l.getRole()+"];\n");
			}
		}
		
		ecrivain.write("} \n");
		ecrivain.close();
		
		System.out.println(" done :)");
		callDot(filePath);
	}
	
	private void callDot(String fileName){
		String cmd = "dot -Tpng "+fileName +" -o "+fileName+".png";
		
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(cmd);		
			System.out.println("A png file "+fileName+".png is generated");		  
		}
		catch(Exception e)
		{
			System.out.println("GraphViz Software is not installed");
		}
	}

}
