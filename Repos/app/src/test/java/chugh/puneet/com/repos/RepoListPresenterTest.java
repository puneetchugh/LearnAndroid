package chugh.puneet.com.repos;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import chugh.puneet.com.repos.model.data.AllRepos;
import chugh.puneet.com.repos.model.data.Repo;
import chugh.puneet.com.repos.model.network.NetworkClient;
import chugh.puneet.com.repos.model.network.NetworkService;
import chugh.puneet.com.repos.presentors.ReposListPresentor;
import chugh.puneet.com.repos.views.view_interfaces.ReposListView;
import rx.Observable;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepoListPresenterTest {


    @Mock
    private ReposListView reposListView;
    private ReposListPresentor presentor;
    @InjectMocks
    private NetworkClient client;

    @Mock
    NetworkService service;

    @Mock
    List<Repo> repos;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void init(){
        presentor = new ReposListPresentor(client);
        presentor.attachView(reposListView);
    }

    @Test
    public void testAttach(){
        assertNotNull(presentor.view);
    }

    @Test
    public void testDetach(){
        presentor.detachView();
        assertNull(presentor.view);
    }

    @Test
    public void testFetchData(){

        Repo repo1 = new Repo();
        Repo repo2 = new Repo();
        AllRepos allrepos = new AllRepos();
        List<Repo> repoList = new LinkedList<>();
        repo1.setFullName("Microsoft/vscode");
        repo1.setHtmlUrl("https://github.com/Microsoft/vscode");
        repo1.setStargazersCount(1000);
        repo1.setName("vscode");

        repo2.setFullName("Microsoft/TypeScript");
        repo2.setHtmlUrl("https://github.com/Microsoft/TypeScript");
        repo2.setStargazersCount(999);
        repo2.setName("TypeScript");

        repoList.add(repo1);
        repoList.add(repo2);

        allrepos.setTotalCount(2);
        allrepos.setIncompleteResults(false);
        allrepos.setItems(repoList);

        Observable<AllRepos> responseAllRepos = Observable.just(allrepos);

        when(service.getRepos(anyString(), anyString(), anyString())).thenReturn(responseAllRepos);

        //testing a positive case
        presentor.fetchList("microsoft");

        InOrder inOrder = Mockito.inOrder(reposListView);

        inOrder.verify(reposListView).displayList(any());
        inOrder.verify(reposListView).hideProgressBar();

        //testing a negative scenario
        presentor.fetchList("");
        verify(reposListView, times(2)).hideProgressBar();
        verify(reposListView).displayError(any());

        //testing another negative scenario
        presentor.fetchList(null);
        verify(reposListView, times(3)).hideProgressBar();
        verify(reposListView, times(2)).displayError(any());

    }


}
