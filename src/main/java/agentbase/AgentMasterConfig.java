/**
 *
 */
package agentbase;

import generatorbase.Distribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import simbase.RatingManager;
import trustmodel.TrustModel;

import modelbase.AgentLogicModel;
import modelbase.Entity;
import modelbase.IdentityLogic;
import modelbase.PurchaseLogic;
import modelbase.PurchaseLogicWishlist;
import modelbase.RatingLogic;
import configbase.AgentConfig;
import configbase.Config;
import configbase.DistributionConfig;
import core.Configurable;

/**
 * This is use on per-config file basis.
 * 
 * @author akai
 * 
 */
public class AgentMasterConfig extends Config implements Configurable {

	public Class<PurchaseLogic>	purchaseLogicClass;
	public Class<RatingLogic>	ratingLogicClass;
	public Class<TrustModel>	trustModelClass;
	public int					agentNum;
	public String				masterName;
	public AgentMaster			master;
	public RatingManager		ratingManager;
	public String				trustModelConfigFile;
	public Class<IdentityLogic>	identityLogicClass;

	/* (non-Javadoc)
	 * 
	 * @see configbase.Config#configure(modelbase.Entity) */
	@Override
	public void configure(Entity e) {
		Buyer buyer = (Buyer) e;
		try {
			buyer.setPurchaseLogic(purchaseLogicClass.newInstance());
			buyer.getPurchaseLogic().setConfig(this);
			if (trustModelClass != null) {
				buyer.getPurchaseLogic().trustModel = trustModelClass.newInstance();
				buyer.getPurchaseLogic().trustModel = (TrustModel) Config.config(trustModelClass,
						trustModelConfigFile);
			}
			buyer.getPurchaseLogic().config();
			buyer.setRatingLogic(ratingLogicClass.newInstance());
			buyer.getRatingLogic().setConfig(this);
			buyer.getRatingLogic().config();
			if (identityLogicClass != null) {
				buyer.setIdentityLogic(identityLogicClass.newInstance());
				buyer.getIdentityLogic().setAgent(buyer);
				buyer.getIdentityLogic().setConfig(this);
				buyer.getIdentityLogic().config();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see core.Configurable#getConfigAttributes() */
	public String[] getConfigAttributes() {
		String[] list = { "masterName", "purchaseLogicClass", "agentNum", "ratingLogicClass",
				"trustModelClass", "trustModelConfigFile", "identityLogicClass" };
		return list;
	}

	/* (non-Javadoc)
	 * 
	 * @see configbase.Config#processConfigKey(java.lang.String,
	 * java.lang.String) */
	@Override
	public boolean processConfigKey(String key, String value) {
		return false;
	}
}
