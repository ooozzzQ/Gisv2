package serviceimp;

import java.util.List;

import dao.SearchCarDao;
import model.Car;
import service.SearchCarServiceInterface;
import util.PagesSource;

public class SearchCarServiceImpl implements SearchCarServiceInterface {
	
	private  SearchCarDao searchCarDao;
	
	
	public SearchCarServiceImpl(SearchCarDao searchCarDao) {
		this.searchCarDao = searchCarDao;
	}


	public SearchCarServiceImpl() {
		
	}


	public SearchCarDao getSearchCarDao() {
		return searchCarDao;
	}


	public void setSearchCarDao(SearchCarDao searchCarDao) {
		this.searchCarDao = searchCarDao;
	}


	public List<Car> getCarByPage(PagesSource page) {
		return searchCarDao.getCarByPage(page);
	}


	public int getCarTotalCount() {
		return searchCarDao.getCarTotalCount();
	}

}
