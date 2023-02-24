package monpackage;

public class MyStack{
	private String [] arr;
	private int _top;

	public MyStack(int size)
	{
		arr = new String[size];
		_top = -1;
	}

	public void Push(boolean b){
		String a = null;
		if(_top == arr.length-1) {}

		else{
			arr[++_top] =a ;

		}

	}



	public String Pop(){
		if(_top == -1){
			return "empty";
		}
		else
			return arr[_top--];

	}
	public int getTop() {
		return _top;
	}
	public String Peek(){

		return arr[_top];
	}
	public String data(int top){

		return arr[top];
	}
	public int search(int e)
	{
		return -1;
	}

}