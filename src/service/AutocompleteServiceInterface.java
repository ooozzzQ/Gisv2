package service;

import java.util.List;
import model.Car;

public interface AutocompleteServiceInterface {
	public  List<String> getCarIdList(String wordText);
}
