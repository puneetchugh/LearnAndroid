package chugh.puneet.com.repos;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import chugh.puneet.com.repos.presentors.ResultsPresentor;
import chugh.puneet.com.repos.views.view_interfaces.ResultsView;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ResultsPresenterTest {
    @Mock
    private ResultsView resultsView;
    private ResultsPresentor resultsPresentor;

    @Before
    public void init(){
        resultsPresentor = new ResultsPresentor();
        resultsPresentor.attachView(resultsView);
    }

    @Test
    public void testAttach(){
        resultsPresentor.attachView(resultsView);
        assertNotNull(resultsView);
    }

    @Test
    public void testDetach(){
        resultsPresentor.detachView();
        assertNull(resultsPresentor.view);
    }

    @Test
    public void testOrgNamePositive(){
        resultsPresentor.onResultsActivityCalled("org:google");
        verify(resultsView, times(1)).callListFragment("org:google");
    }

    @Test
    public void testOrgNameNegative1(){
        resultsPresentor.onResultsActivityCalled(null);
        verify(resultsView, times(1)).showError();
    }

    @Test
    public void testOrgNameNegative2(){
        resultsPresentor.onResultsActivityCalled("");
        verify(resultsView, times(1)).showError();
    }

}
