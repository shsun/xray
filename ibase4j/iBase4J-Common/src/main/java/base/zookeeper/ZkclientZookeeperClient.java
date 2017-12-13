package base.zookeeper;

import java.util.List;

import com.alibaba.dubbo.remoting.zookeeper.StateListener;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.zookeeper.ChildListener;
import com.alibaba.dubbo.remoting.zookeeper.support.AbstractZookeeperClient;

/**
 * Created by shsun on 12/13/17.
 */
public class ZkclientZookeeperClient extends AbstractZookeeperClient<IZkChildListener> {

    private final ZkClient client;

    private volatile KeeperState state;

    public ZkclientZookeeperClient(URL url) {
        super(url);
        this.state = KeeperState.SyncConnected;
        // 设置超时时间为5000毫秒
        this.client = new ZkClient(url.getBackupAddress(), 5000);
        this.client.subscribeStateChanges(new IZkStateListener() {
            @Override
            public void handleStateChanged(KeeperState state) throws Exception {
                ZkclientZookeeperClient.this.state = state;
                if (state == KeeperState.Disconnected) {
                    ZkclientZookeeperClient.this.stateChanged(StateListener.DISCONNECTED);
                } else if (state == KeeperState.SyncConnected) {
                    ZkclientZookeeperClient.this.stateChanged(StateListener.CONNECTED);
                }
            }

            @Override
            public void handleNewSession() throws Exception {
                ZkclientZookeeperClient.this.stateChanged(StateListener.RECONNECTED);
            }

            @Override
            public void handleSessionEstablishmentError(Throwable throwable) throws Exception {
                ZkclientZookeeperClient.this.stateChanged(StateListener.DISCONNECTED);

            }
        });
    }

    public void createPersistent(String path) {
        try {
            this.client.createPersistent(path, true);
        } catch (ZkNodeExistsException var3) {
            ;
        }

    }

    public void createEphemeral(String path) {
        try {
            this.client.createEphemeral(path);
        } catch (ZkNodeExistsException var3) {
            ;
        }

    }

    public void delete(String path) {
        try {
            this.client.delete(path);
        } catch (ZkNoNodeException var3) {
            ;
        }

    }

    public List<String> getChildren(String path) {
        try {
            return this.client.getChildren(path);
        } catch (ZkNoNodeException var3) {
            return null;
        }
    }

    public boolean isConnected() {
        return this.state == KeeperState.SyncConnected;
    }

    public void doClose() {
        this.client.close();
    }

    public IZkChildListener createTargetChildListener(String path, final ChildListener listener) {
        return new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                listener.childChanged(parentPath, currentChilds);
            }
        };
    }

    public List<String> addTargetChildListener(String path, IZkChildListener listener) {
        return this.client.subscribeChildChanges(path, listener);
    }

    public void removeTargetChildListener(String path, IZkChildListener listener) {
        this.client.unsubscribeChildChanges(path, listener);
    }

}