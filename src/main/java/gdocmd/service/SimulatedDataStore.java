package gdocmd.service;

import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public class SimulatedDataStore<V extends Serializable> implements DataStore<V> {
  @Override
  public DataStoreFactory getDataStoreFactory() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getId() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int size() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isEmpty() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsKey(String s) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsValue(V v) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<String> keySet() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<V> values() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public V get(String s) throws IOException {
    System.out.println(s);
//    throw new UnsupportedOperationException();
    return null;
  }

  @Override
  public DataStore<V> set(String s, V v) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public DataStore<V> clear() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public DataStore<V> delete(String s) throws IOException {
    throw new UnsupportedOperationException();
  }
}
