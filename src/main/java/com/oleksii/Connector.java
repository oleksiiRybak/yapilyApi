package com.oleksii;

import java.util.List;

public interface Connector {
	
	public List fetchAllRecords();
	public Character fetchRecordById(String id);
}
