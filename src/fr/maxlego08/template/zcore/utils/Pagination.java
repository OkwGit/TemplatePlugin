package fr.maxlego08.template.zcore.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class allowing to paginate a list or a map.
 *
 * @param <T> Type of the object to paginate.
 * @author Maxlego08
 */
public class Pagination<T> {


    /**
     * Paginate a list in reverse order.
     *
     * @param list          The list to paginate.
     * @param inventorySize The size of the page.
     * @param page          The page number.
     * @return The current page.
     */
    public List<T> paginateReverse(List<T> list, int inventorySize, int page) {
        List<T> currentList = new ArrayList<>();
        if (page == 0) page = 1;
        int idStart = list.size() - 1 - ((page - 1) * inventorySize);
        int idEnd = idStart - inventorySize;
        if (idEnd < list.size() - inventorySize && list.size() < inventorySize * page) idEnd = -1;
        for (int a = idStart; a != idEnd; a--)
            currentList.add(list.get(a));
        return currentList;
    }


    /**
     * Paginate a list.
     *
     * @param list The list to paginate.
     * @param size The size of the page.
     * @param page The page number.
     * @return The current page.
     */
    public List<T> paginate(List<T> list, int size, int page) {
        List<T> currentList = new ArrayList<>();
        if (page <= 0) page = 1;
        int idStart = ((page - 1)) * size;
        int idEnd = idStart + size;
        if (idEnd > list.size()) idEnd = list.size();
        for (int a = idStart; a != idEnd; a++)
            currentList.add(list.get(a));
        return currentList;
    }

    /**
     * Paginate a map in reverse order.
     *
     * @param map  The map to paginate.
     * @param size The size of the page.
     * @param page The page number.
     * @return The current page.
     */
    public List<T> paginateReverse(Map<?, T> map, int size, int page) {
        return paginateReverse(new ArrayList<>(map.values()), size, page);
    }


    /**
     * Paginate a map.
     *
     * @param map           The map to paginate.
     * @param inventorySize The size of the page.
     * @param page          The page number.
     * @return The current page.
     */
    public List<T> paginate(Map<?, T> map, int inventorySize, int page) {
        return paginate(new ArrayList<>(map.values()), inventorySize, page);
    }

}