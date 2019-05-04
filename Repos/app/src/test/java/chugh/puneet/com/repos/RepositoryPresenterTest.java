package chugh.puneet.com.repos;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import chugh.puneet.com.repos.presentors.RepositoryPresentor;
import chugh.puneet.com.repos.views.view_interfaces.RepositoryView;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryPresenterTest {

    @Mock
    RepositoryView repositoryView;
    RepositoryPresentor presentor;

    @Before
    public void init(){
        presentor = new RepositoryPresentor();
        presentor.attachView(repositoryView);
    }

    @Test
    public void testAttach(){
        presentor.attachView(repositoryView);
        assertNotNull(presentor.view);
    }

    @Test
    public void testLoadURL(){
        presentor.loadRepo("https://github.com/Microsoft/microsoft.github.io");
        verify(repositoryView, times(1)).displayRepository("https://github.com/Microsoft/microsoft.github.io");
    }

    @Test
    public void testLoadURLNegative1(){
        presentor.loadRepo(null);
        verify(repositoryView, times(1)).displayError();
    }

    @Test
    public void testLoadURLNegative2(){
        presentor.loadRepo("");
        verify(repositoryView, times(1)).displayError();
    }
}
