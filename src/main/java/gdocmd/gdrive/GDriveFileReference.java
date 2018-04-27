package gdocmd.gdrive;

public class GDriveFileReference {
  public final String id;
  public final String name;
  public final String modifiedTimestampUTC;

  public GDriveFileReference(String id, String name, String modifiedTimestampUTC) {
    this.id = id;
    this.name = name;
    this.modifiedTimestampUTC = modifiedTimestampUTC;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GDriveFileReference that = (GDriveFileReference) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    return modifiedTimestampUTC != null ? modifiedTimestampUTC.equals(that.modifiedTimestampUTC) : that.modifiedTimestampUTC == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (modifiedTimestampUTC != null ? modifiedTimestampUTC.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "GDriveFileReference{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      ", modifiedTimestampUTC='" + modifiedTimestampUTC + '\'' +
      '}';
  }
}
