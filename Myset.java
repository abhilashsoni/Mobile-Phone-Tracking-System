import java.util.NoSuchElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

class MobilePhone
{//complete
	int mobileno;
	Boolean status;
	Exchange basest;
	MobilePhone next;
	MobilePhone(int number)
	{
		//constructor to create a mobile phone.
		mobileno = number;
		status = true;
		basest = null;
		next = null;
		
	}

	MobilePhone(MobilePhone k)
	{
		mobileno = k.mobileno;
		status = k.status;
		basest = k.basest;
		next = k.next;

	}

	MobilePhone(MobilePhone k, Exchange l)
	{
		mobileno = k.mobileno;
		status = true;
		basest = l;
		next = null;

	}

	public int number()
	{
		return mobileno; //returns the id of the mobile phone.
	}

	public Boolean status()
	{
		return status; //returns the status of the phone 
	}

	public void switchOn()
	{
		status = true;//changes the status to on
	}

	public void switchoff()
	{
		status = false;
	}

	public Exchange location()
	{
		//returns the base station with which the phone is registered if the phone is swithed on and an exception if phone is off
		if(status())
			{return basest;}
		else 
		{//throw an exception
			try
			{
			throw new NoSuchElementException("no is switched off"); 
			}
			catch(NoSuchElementException e)
			{
				System.out.println("Error: mobile is switched off");
			} 
			return null; 
		}
	}
}

class MobilePhoneSet
{
	//stores MobilePhone Objects in a Myset
	public Myset mobiles = new Myset();

}

class Exchangelist
{
	//implements a linked list of exchanges
	Exchange front, rear;
	int size;
	Boolean isEmpty() {return (front==null);}
	public void insert(Exchange k) 
		 {
		 	Exchange newnode = new Exchange(k);
		 	if(front==null)
		 	{
		 		front = newnode; 
		 		rear = newnode;
		 	}
		 	else
		 	{
		 		rear.next=newnode;
		 		rear = rear.next;
		 	}
		 	size++;
		 }
	public Boolean contains(Exchange k)
	{
		if(!isEmpty())
		{
			Exchange t = new Exchange(front);
			while(t!=null)
			{
				if(t.exchangeno == k.exchangeno)
					return true;
				else t = t.next;
			};
			return false;
		}
		

		else 
		{//throw an exception
			try
			{
			throw new NoSuchElementException(); 
			}
			catch(NoSuchElementException e)
			{
				System.out.println("Error: The list is empty");
			} 
			return false; 
		}
		
	}
}

class Exchange
{
	//form the nodes of the routing map structure
	int exchangeno;
	Exchange parent;
	int noc;
	Exchange child;
	Exchange next;
	public Exchange(int number)
	{
		//constructor to create an exchange
		exchangeno = number;
		parent = null;
		noc=0;
		child = null;
		next = null;
	}

	public void init(int number)
	{
		exchangeno = number;
		parent = null;
		noc=0;
		child = null;
		next = null;
	}

	public Exchange(Exchange k)
	{
		exchangeno = k.exchangeno;
		parent = k.parent;
		noc = k.noc;
		child = k.child;
		next = k.next;
	}

	public Exchange parent()
	{
		//for parent
		return parent;
	}

	public void add(Exchange d)
	{
		child = d;
		child.parent = this;
		noc++;
	}

	public int numChildren()
	{  // it hould be int return type
		//for no. of children
		return noc;
	}

	public Exchange child(int i)
	{
		//returns ith child
		int j=0;
		Exchange chi = child;
		//System.out.println(noc+" "+i);
		if(i<noc)
		{   
			while(j<i)
			{
				chi = chi.next;
				j++;
			}
			return chi;
		}
		else 
		{//throw an exception
			try
			{
			throw new NoSuchElementException(); 
			}
			catch(NoSuchElementException e)
			{
				System.out.println("Error: There are not so many children");
			} 
			return null; 
		}

	}

	public Boolean isRoot()
	{
		if(parent==null)
			return true;
		else return false;
	}
}

public class Myset
{//complete0
	MobilePhone front,rear;
	int size;
	public Boolean IsEmpty() 
	{
		//returns true if the set is empty
		return(front==rear);
	}

	public Boolean IsMember(MobilePhone o)
	{
		//returns true if o is in the set, false otherwise
		MobilePhone ph = front;
		while(ph!=null)
		{
			if(ph.mobileno == o.mobileno)
				return true;
			else ph = ph.next;
		};
		return false;
	}

	public Exchange Member(MobilePhone o)
	{
		//returns true if o is in the set, false otherwise
		MobilePhone ph = front;
		while(ph!=null)
		{
			if(ph.mobileno == o.mobileno)
				return ph.basest;
			else ph = ph.next;
		}
		return null;
	}

	public void Insert(MobilePhone o, Exchange p)
	{
		//inserts o into the set
		MobilePhone newmob = new MobilePhone(o,p);
		 	if(front==null)
		 	{
		 		front = newmob; 
		 		rear = newmob;
		 	}
		 	else
		 	{
		 		rear.next=newmob;
		 		rear = rear.next;
		 	}
		 	size++;
	}

	public void Delete(MobilePhone o)
	{
		//deletes o from the set, throws exception if o is not in the set
		if(IsEmpty()) throw new NoSuchElementException("Underflow Exception");
		if(front.mobileno == o.mobileno){
		 				front=front.next;
		 				return;
		 			}
		 		MobilePhone l=front;
		 		while(!(l.next.mobileno==o.mobileno))
		 		{
		 			l=l.next;
		 		}
		 		l.next=l.next.next;
	}
}