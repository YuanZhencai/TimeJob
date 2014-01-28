package com.wcs.filenet;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.core.Document;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.ibm.filenet.helper.ce.ObjectStoreProvider;
import com.ibm.filenet.helper.ce.Search;

public class CETest {
    private static Logger logger = Logger.getLogger(CETest.class);

    /**
     * <p>
     * Description:
     * </p>
     * 
     * @param args
     */
    public static void main(String[] args) {
        ObjectStoreProvider provider = new ObjectStoreProvider("fnadmin", "fnadmin123");
        ObjectStore objectStore = provider.getObjectStore();

//        String className = "TihDoc";
//        String whereClause = null;
//        boolean includeSubclasses = false;
//        Search search = new Search();
//        List<Document> documents = search.setObjectSql(className, includeSubclasses, whereClause).setMaxRecords(10)
//                .setScope(objectStore).fetchRows();
//        for (Document document : documents) {
//            logger.debug("[id]" + document.get_Id());
//        }

        SearchScope searchScope = new SearchScope(objectStore);

        SearchSQL sql = new SearchSQL();
        sql.setSelectList("d.*");
        sql.setFromClauseInitialValue("Tihdoc", "d", false);
        sql.setMaxRecords(10);
//        String whereClause = "";
//        sql.setWhereClause(whereClause);
        logger.info("[sql]"+sql.toString());
        DocumentSet fetchObjects = (DocumentSet) searchScope.fetchObjects(sql, 10, null, true);
        Iterator iterator = fetchObjects.iterator();

        while (iterator.hasNext()) {
            Document document = (Document) iterator.next();
            logger.info("[id]" + document.get_Id());
        }
        // List<Document> documents = provider.searchObjects(className, whereClause, includeSubclasses, 10);
        // for (Document document : documents) {
        // logger.debug("[id]" + document.get_Id());
        // }

    }

}
