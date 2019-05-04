package chugh.puneet.com.repos.views.view_interfaces;

import android.view.View;

public interface SearchView {
    void moveToNextPage(String string);
    void searchClicked(View view);
    void notifyEmptyString();
}
