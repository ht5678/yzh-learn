package mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年11月26日 下午2:03:25  
 *
 * @param <T>
 */
public abstract class BaseMongoDao<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * 保存一个对象
     * @param t
     */
    public void save(T t) {
        this.mongoTemplate.save(t);
    }

    /**
     * 批量保存对象
     * @param list
     */
    public void insertList(List<T> list) {
        this.mongoTemplate.insertAll(list);
    }

    /**
     * 查询所有
     * @return
     */
    public List<T> queryAll() {
        return this.mongoTemplate.findAll(this.getEntityClass());
    }

    /**
     * 根据Id从Collection中查询对象
     * @param id
     * @return
     */
    public T queryById(String id) {
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id);
        query.addCriteria(criteria);
        return this.mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 根据条件查询集合
     * @param query
     * @return
     */
    public List<T> queryList(Query query) {
        return this.mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 通过条件查询单个实体
     * @param query
     * @return
     */
    public T queryOne(Query query) {
        return this.mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 通过条件进行分页查询
     * @param query
     * @param start
     * @param size
     * @return
     */
    public List<T> getPage(Query query, int start, int size) {
        query.skip(start);
        query.limit(size);
        List<T> lists = this.mongoTemplate.find(query, this.getEntityClass());
        return lists;
    }

    /**
     * 根据条件查询库中符合记录的总数,为分页查询服务
     * @param query
     * @return
     */
    public Long getPageCount(Query query) {
        return this.mongoTemplate.count(query, this.getEntityClass());
    }

    /**
     * 根据Id删除用户
     * @param id
     */
    public void deleteById(String id) {
        Criteria criteria = Criteria.where("_id").in(id);
        if (null != criteria) {
            Query query = new Query(criteria);
            if (null != query && this.queryOne(query) != null) {
                this.mongoTemplate.remove(query);
            }
        }
    }

    /**
     * 更新满足条件的第一个记录
     * @param query
     * @param update
     */
    public void updateFirst(Query query, Update update) {
        this.mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }

    /**
     * 更新满足条件的所有记录
     * @param query
     * @param update
     */
    public void updateMulti(Query query, Update update) {
        this.mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    /**
     * 查找更新,如果没有找到符合的记录,则将更新的记录插入库中
     * @param query
     * @param update
     */
    public void updateInser(Query query, Update update) {
        this.mongoTemplate.upsert(query, update, this.getEntityClass());
    }

    /**
     * 删除对象
     * @param t
     * @return
     */
    public Integer delete(T t) {
        WriteResult writeResult = this.mongoTemplate.remove(t);
        return (null == writeResult ? 0 : writeResult.getN());

    }

    /**
     * 删除对象
     * @param query
     * @return
     */
    public Integer deleteByCondition(Query query) {
        WriteResult writeResult = this.mongoTemplate.remove(query, this.getEntityClass());
        return (null == writeResult ? 0 : writeResult.getN());

    }

    /**
     * 为属性自动注入bean服务
     * @param mongoTemplate
     */
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected abstract Class<T> getEntityClass();
}
