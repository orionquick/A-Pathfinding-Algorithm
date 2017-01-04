package main;

import java.util.ArrayList;

public class Square {
	
	Square child;
	
	Point pos = new Point(0, 0);
	int hValue = 0;
	int mValue = 0;
	int fValue = hValue + mValue;
	boolean isParent = false;
	boolean isValid = true;
	
	ArrayList<Square> parents = new ArrayList<Square>();
	
	void fUpdate()
	{
		fValue = hValue + mValue;
	}
}
