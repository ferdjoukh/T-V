import java.io.IOException;

import Ecore.EcoreFileReader;
import Model.*;
/**
 * 
 * @author Adel Ferdjoukh
 *
 */
public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		EcoreFileReader efr= new EcoreFileReader("files/ER.ecore", "Schema");
		
		
		//System.out.println(efr.toString());
		
		
		
		VERIATLFileReader reader= new VERIATLFileReader("files/s28.veriatl","files/ER.ecore", "Schema");
		reader.generateModel();
		Model model=reader.getModel();
		System.out.println(model.toString());
		model.generateDotFile("files/s28.dot");
		
	}

}
