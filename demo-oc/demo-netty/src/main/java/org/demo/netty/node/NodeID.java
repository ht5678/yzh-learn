package org.demo.netty.node;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午4:53:51
 */
public class NodeID implements Externalizable{
	
	private byte[] nodeID;
	
	
	public NodeID() {
		
	}
	
	
	public NodeID(byte[] nodeID) {
		this.nodeID = nodeID;
	}
	
	
	
    public byte[] getBytes() {
        return nodeID;
    }
	
	
	
	

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(nodeID.length);
        out.write(nodeID);
    }

    @Override
    public String toString() {
        return new String(nodeID, StandardCharsets.UTF_8);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        nodeID = new byte[in.readInt()];
        in.readFully(nodeID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NodeID that = (NodeID) o;

        return Arrays.equals(nodeID, that.nodeID);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nodeID);
    }
    
    
    
    
    
    
    


	public byte[] getNodeID() {
		return nodeID;
	}


	public void setNodeID(byte[] nodeID) {
		this.nodeID = nodeID;
	}
	

	
}
