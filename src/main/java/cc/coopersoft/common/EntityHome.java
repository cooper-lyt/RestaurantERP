package cc.coopersoft.common;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by cooper on 6/10/16.
 */
public abstract class EntityHome<E, PK extends Serializable> extends MutableController {

    @Inject
    protected Logger logger;

    private static final long serialVersionUID = -5462396456614090423L;

    private PK id;
    protected E instance;


    /**
     * Get the managed entity, using the id from {@link #getId()} to load it from
     * the Persistence Context or creating a new instance if the id is not
     * defined.
     *
     * @see #getId()
     */
    @Transactional
    public E getInstance()
    {
        if (instance==null)
        {
            initInstance();
        }
        return instance;
    }

    /**
     * Clear the managed entity (and id), allowing the {@link EntityHome} to be
     * reused.
     */
    public void clearInstance()
    {
        setInstance(null);
        setId(null);
    }

    /**
     * Load the instance if the id is defined otherwise create a new instance
     * <br />
     * Utility method called by {@link #getInstance()} to load the instance from
     * the Persistence Context if the id is defined. Otherwise a new instance is
     * created.
     *
     * @see #find()
     * @see #createInstance()
     */
    protected void initInstance()
    {
        if ( isIdDefined() )
        {

            setInstance( find() );

        }
        else
        {
            setInstance( createInstance() );
        }
    }


    /**
     * Create a new instance of the entity.
     * <br />
     * Utility method called by {@link #initInstance()} to create a new instance
     * of the entity.
     */
    protected abstract E createInstance();



    /**
     * Get the id of the object being managed.
     */
    public PK getId()
    {
        return id;
    }

    /**
     * Set/change the entity being managed by id.
     *
     *
     */
    public void setId(PK id)
    {
        if ( setDirty(this.id, id) ) setInstance(null);
        this.id = id;
    }


    /**
     * Returns true if the id of the object managed is known.
     */
    public boolean isIdDefined()
    {
        return getId()!=null && !"".equals( getId() );
    }

    /**
     * Set/change the entity being managed.
     */
    public void setInstance(E instance)
    {
        setDirty(this.instance, instance);
        this.instance = instance;
    }


    /**
     * Flush any changes made to the managed entity instance to the underlying
     * database.
     * <br />
     * If the update is successful, a log message is printed, a
     * {@link javax.faces.application.FacesMessage} is added and a transaction
     * success event raised.
     *
     * @return "updated" if the update is successful
     */

    @Transactional
    public void save()
    {

        setInstance(getEntityRepository().saveAndFlushAndRefresh(getInstance()));

        logger.config("save entity");
    }



    /**
     * Remove managed entity instance from the Persistence Context and the
     * underlying database.
     * If the remove is successful, a log message is printed, a
     * {@link javax.faces.application.FacesMessage} is added and a transaction
     * success event raised.
     *
     *
     * @return "removed" if the remove is successful
     */
    @Transactional
    public void remove()
    {
        getEntityRepository().removeAndFlush(getInstance());

    }


    @Transactional
    public E find()
    {

            E result = loadInstance();
            if (result==null)
            {
                throw new EntityNotFoundException( getId() );
            }
            return result;

    }

    @Inject
    private EntityManager entityManager;

    @Transactional
    public boolean isManaged()
    {

        return getInstance()!=null &&
                entityManager.contains( getInstance() );
    }



    /**
     * Called by {@link #find()}.
     * <br />
     * Can be overridden to support eager fetching of associations.
     *
     */
    protected E loadInstance()
    {
        return getEntityRepository().findBy(getId());
    }



    protected abstract EntityRepository<E,PK> getEntityRepository();



}
