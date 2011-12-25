/**
 *
 */
package modelbase;

import agentbase.Buyer;
import simbase.Transaction;
import core.BaseObject;

/**
 * @author akai
 * 
 */
public abstract class PurchaseLogic extends BaseObject {
	protected Buyer	buyer;

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public abstract Transaction transact();
}