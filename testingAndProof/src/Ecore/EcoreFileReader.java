package Ecore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * 
 * @author Adel Ferdjoukh
 * 
 * This class is used to read an Ecore file. 
 * It bring many methods to handle Ecore meta-models: 
 * 		get classes, attributes, references, etc.
 *
 */
public class EcoreFileReader {

	private Resource resource;
	private EPackage BasePackage;
	private String rootClass;
	private String path;
		
	public String getRootClass() {
		return rootClass;
	}

	public EcoreFileReader(String str,String root){
		
		 Resource.Factory.Registry reg=Resource.Factory.Registry.INSTANCE;
		 Map<String,Object> m = reg.getExtensionToFactoryMap();
		 m.put("ecore",new XMIResourceFactoryImpl());
		 ResourceSet resourceSet=new ResourceSetImpl();
		 URI fileURI=URI.createFileURI(str);
		 Resource resource=resourceSet.getResource(fileURI,true);
		
		this.resource=resource;
		
		EPackage c= (EPackage)  resource.getContents().get(0);
		
		this.BasePackage= c;
		this.rootClass=root;
		this.path=str;
	}
	
	public Resource getModelResource()
	{
		return this.resource;
	}
	
	public EPackage getModelPackage()
	{
		return this.BasePackage;
	}
	
	public List<EClass> getClasses()
	{
		ArrayList<EClass> cls= new ArrayList<EClass>();
		for( EClassifier cf :BasePackage.getEClassifiers())
		{
			if (cf instanceof EClass)
			{
					if (!((EClass) cf).isAbstract())
						cls.add((EClass) cf);
										
			}
		}
		
		
		return cls;
	}
	
	public List<EClass> getAbtractClasses()
	{
		ArrayList<EClass> cls= new ArrayList<EClass>();
		for( EClassifier cf :BasePackage.getEClassifiers())
		{
			if (cf instanceof EClass)
			{
					if (((EClass) cf).isAbstract())
						cls.add((EClass) cf);
			}
		}
		return cls;
	}
	
	public int getClassIndex(EClass c)
	{
		int i=1;
		ArrayList<EClass> cls= (ArrayList<EClass>) getClasses();
		for (EClass cc: cls)
		{
			if(cc==c)
				return i;
			else
				i++;
		}
		return (Integer) null;
	}
	
	public int getClassIndex(String c)
	{
		int i=1;
		ArrayList<EClass> cls= (ArrayList<EClass>) getClasses();
		for (EClass cc: cls)
		{
			if(cc.getName().equals(c))
				return i;
			else
				i++;
		}
		System.out.println(c);
		return (Integer) null;
	}
	
	public List<EAttribute> getAllAttributesFromClass(EClass c)
	{
		ArrayList<EAttribute> attr= new ArrayList<EAttribute>();
		attr.addAll(c.getEAllAttributes());
		return attr;
	}
	
	public List<EClass> getAllSubtypes(EClass c)
	{
		ArrayList<EClass> cls= new ArrayList<EClass>();
		for(EClass cc: getClasses())
		{
			if(cc.getEAllSuperTypes().contains(c))
			cls.add(cc);
		}
		return cls;
	}
	
	public List<EReference> getAllReferencesFromClass(EClass c)
	{
		ArrayList<EReference> refs= new ArrayList<EReference>();
		refs.addAll(c.getEAllReferences());
		
		return refs;
    }
	
	public List<EReference> getAllReferencesFromClasswithOpposite(EClass c)
	{
		ArrayList<EReference> refs= new ArrayList<EReference>();
		//refs.addAll(c.getEAllReferences());
		for (EReference ref: c.getEAllReferences())
		{
			if(ref.getEOpposite()==null)
			{
				//Si elle n'a pas de EOpposite: l'ajouter
				refs.add(ref);
			}
			else
			{
				//Si elle a une EOpposite: Ajouter une des deux;
				int i=ref.getEOpposite().getUpperBound();
				if (i==-1) i=100;
				int j=ref.getUpperBound();
				if (j==-1) j=100;			
				if(j<i)
				{
					refs.add(ref);
				}
				else if(i==j)
				{
					if(ref.getName().codePointCount(0, ref.getName().length())< ref.getEOpposite().getName().codePointCount(0, ref.getEOpposite().getName().length()))
					{
						refs.add(ref);
					}
				}
			}
		}
		return refs;
    }
	
	public List<EClass> getConcreteSubTypes(EClass c) 
	{

			ArrayList<EClass> cls= new ArrayList<EClass>();
			for(EClass cc: getClasses())
			{
				if (!cc.isAbstract())
				{
				if(cc.getEAllSuperTypes().contains(c))	
				cls.add(cc);
				}
			}
			return cls;
	}
	
	public String toString(){
		String str=  path+" [Classes: ";
		
		for (EClass c: this.getClasses()){
			str= str+ c.getName()+", ";
		}
		
		str= str.substring(0, str.length()-2);
		return str+"]";
		
	}
	
	public EClass getClassByName(String className){
		EClass cl=null;
		for(EClass c:getClasses()){
			if(c.getName().equals(className))
				cl=c;
		}
		return cl;
		
	}
	
	public EReference getReferenceByName(String className, String refName){
		
		EClass cl=getClassByName(className);
		EReference ref=null;		
		if(cl!=null)
		{
			for(EReference i: getAllReferencesFromClass(cl))
			{
				if(i.getName().equals(refName))
					ref=i;					
			}
		}
		return ref;
	}
	
}
