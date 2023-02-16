package gb.org;

public class Persister{
	private Note note;

	public Persister(Note note){
		this.note = note;
	}

	public void save(){
		System.out.println("Save user: " + note.getNameNote());
	}



}