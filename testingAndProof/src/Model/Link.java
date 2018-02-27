package Model;

/**
 * 
 * @author Adel Ferdjoukh
 *
 */
public class Link {
	
	private String role;
	private String type;
	private ClassInstance target;
	
	public Link(String role,String type, ClassInstance targetOID){
		this.role=role;
		this.type=type;
		this.target=targetOID;
	}

	public String getRole() {
		return role;
	}

	public String getType() {
		return type;
	}

	public ClassInstance getTarget() {
		return target;
	}
	
	
}
