package chugh.puneet.com.repos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses ({RepoListPresenterTest.class,
                    RepositoryPresenterTest.class,
                    ResultsPresenterTest.class,
                    SearchPresenterTest.class})
public class UnitTestSuite{}

