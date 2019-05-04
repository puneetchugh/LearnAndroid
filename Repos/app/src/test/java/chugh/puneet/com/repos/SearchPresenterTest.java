package chugh.puneet.com.repos;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import chugh.puneet.com.repos.presentors.SearchPresentor;
import chugh.puneet.com.repos.views.view_interfaces.SearchView;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SearchPresenterTest {

    @Mock
    private SearchView searchView;
    private SearchPresentor presentor;

    @Before
    public void init(){
        this.presentor = new SearchPresentor();
        presentor.attachView(searchView);
    }

    @Test
    public void testAttach(){
        assertNotNull(presentor.view);
    }

    @Test
    public void testActOnSearchClickPositive(){
        presentor.actOnSearchClick("org:google");
        verify(searchView, times(1)).moveToNextPage("org:google");

    }

    @Test
    public void testActOnSearchClickNegative1(){
        presentor.actOnSearchClick("");
        verify(searchView, times(1)).notifyEmptyString();

    }

    @Test
    public void testActOnSearchClickNegative2(){
        presentor.actOnSearchClick(null);
        verify(searchView, times(1)).notifyEmptyString();
    }

    @Test
    public void testDetach(){
        presentor.detachView();
        assertNull(presentor.view);
    }
}
