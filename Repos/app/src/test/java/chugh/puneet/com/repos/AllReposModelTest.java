package chugh.puneet.com.repos;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import chugh.puneet.com.repos.model.data.AllRepos;
import chugh.puneet.com.repos.model.data.Repo;
import rx.observers.TestSubscriber;

public class AllReposModelTest {

    Repo repo1 = new Repo();
    Repo repo2 = new Repo();
    AllRepos allrepos = new AllRepos();
    List<Repo> repoList = new LinkedList<>();
    @Before
    public void init(){
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
    }

    @Test
    public void testSubscriber(){
        TestSubscriber<AllRepos> testSubscriber = new TestSubscriber<>();


    }
}
