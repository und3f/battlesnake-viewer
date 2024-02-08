package name.zasenko.battlesnake.datasource;

public interface CachableDataSource extends DataSource {

    String cachePrefix();
    String itemId();

}
