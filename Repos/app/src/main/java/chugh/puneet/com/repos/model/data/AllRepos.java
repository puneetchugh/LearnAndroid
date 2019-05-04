package chugh.puneet.com.repos.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllRepos {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<Repo> items = null;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Repo> getItems() {
        return items;
    }

    public void setItems(List<Repo> items) {
        this.items = items;
    }
}
