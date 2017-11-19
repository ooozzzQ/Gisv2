package serviceimp;

import java.util.List;

import model.Car;
import dao.SearchCarDao;
import service.AutocompleteServiceInterface;

public class AutocompleteServiceImpl implements AutocompleteServiceInterface {

	private  SearchCarDao searchCarDao;
	
	public SearchCarDao getSearchCarDao() {
		return searchCarDao;
	}
	
	public void setSearchCarDao(SearchCarDao searchCarDao) {
		this.searchCarDao = searchCarDao;
	}

	public List<String> getCarIdList(String wordText) {
		return searchCarDao.getCarIdList(wordText);
	}

}
