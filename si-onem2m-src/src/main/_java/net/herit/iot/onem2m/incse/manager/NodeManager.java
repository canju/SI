package net.herit.iot.onem2m.incse.manager;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.herit.iot.message.onem2m.OneM2mRequest;
import net.herit.iot.message.onem2m.OneM2mResponse;
import net.herit.iot.message.onem2m.OneM2mRequest.OPERATION;
import net.herit.iot.message.onem2m.OneM2mRequest.RESOURCE_TYPE;
import net.herit.iot.onem2m.core.convertor.ConvertorFactory;
import net.herit.iot.onem2m.core.convertor.JSONConvertor;
import net.herit.iot.onem2m.core.convertor.XMLConvertor;
import net.herit.iot.onem2m.core.util.OneM2MException;
import net.herit.iot.onem2m.incse.context.OneM2mContext;
import net.herit.iot.onem2m.incse.controller.AnnounceController;
import net.herit.iot.onem2m.incse.controller.NotificationController;
import net.herit.iot.onem2m.incse.manager.dao.DAOInterface;
import net.herit.iot.onem2m.incse.manager.dao.NodeDAO;
import net.herit.iot.onem2m.resource.Node;
import net.herit.iot.onem2m.resource.Resource;


public class NodeManager extends AbsManager {
	
	static String ALLOWED_PARENT = "CSEBase,remoteCSE";  
	static RESOURCE_TYPE RES_TYPE = RESOURCE_TYPE.NODE; 
	
	private Logger log = LoggerFactory.getLogger(NodeManager.class);

	private static final String TAG = NodeManager.class.getName();

	public NodeManager(OneM2mContext context) {
		super(RES_TYPE, ALLOWED_PARENT);
		this.context = context;
	}
	
	@Override
	public OneM2mResponse create(OneM2mRequest reqMessage) throws OneM2MException {
		//throw new OneM2MException(RESPONSE_STATUS.NOT_IMPLEMENTED, "Not implemented!!!");

		return create(reqMessage, this);
		
	}

	@Override
	public OneM2mResponse retrieve(OneM2mRequest reqMessage) throws OneM2MException {
		//throw new OneM2MException(RESPONSE_STATUS.NOT_IMPLEMENTED, "Not implemented!!!");

		return retrieve(reqMessage, this);
			
	}

	@Override
	public OneM2mResponse update(OneM2mRequest reqMessage) throws OneM2MException {
		//throw new OneM2MException(RESPONSE_STATUS.NOT_IMPLEMENTED, "Not implemented!!!");

		return update(reqMessage, this);
		
	}

	@Override
	public OneM2mResponse delete(OneM2mRequest reqMessage) throws OneM2MException {
		//throw new OneM2MException(RESPONSE_STATUS.NOT_IMPLEMENTED, "Not implemented!!!");

		return delete(reqMessage, this);
	
	}


	@Override
	public OneM2mResponse notify(OneM2mRequest reqMessage) throws OneM2MException {
		// TODO Auto-generated method stub

		return new OneM2mResponse();
	}

	@Override
	public void announceTo(OneM2mRequest reqMessage, Resource resource, Resource orgRes) throws OneM2MException {
		new AnnounceController(context).announce(reqMessage, resource, orgRes, this);
	}
	
	@Override 
	public void notification(Resource parentRes, Resource reqRes, Resource orgRes, OPERATION op) throws OneM2MException {
		Document reqDoc = reqRes == null? null : Document.parse( ((NodeDAO)getDAO()).resourceToJson(reqRes));
		Document orgDoc = orgRes == null? null : Document.parse( ((NodeDAO)getDAO()).resourceToJson(orgRes));
		NotificationController.getInstance().notify(parentRes, reqDoc, reqRes, orgDoc, orgRes, op, this);
	}
	
	@Override
	public DAOInterface getDAO() {
		return new NodeDAO(context);
	}

	@Override
	public Class<?> getResourceClass() {
		return Node.class;
	}

	@Override
	public XMLConvertor<?> getXMLConveter() throws OneM2MException {
		return ConvertorFactory.getXMLConvertor(Node.class, Node.SCHEMA_LOCATION);
	}

	@Override
	public JSONConvertor<?> getJSONConveter() throws OneM2MException {
		return ConvertorFactory.getJSONConvertor(Node.class, Node.SCHEMA_LOCATION);
	}

}
