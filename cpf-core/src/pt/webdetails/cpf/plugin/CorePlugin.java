/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package pt.webdetails.cpf.plugin;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import pt.webdetails.cpf.repository.IRepositoryFile;
import pt.webdetails.cpf.repository.IRepositoryFileFilter;

/**
 * This only exists for locating a plugin.
 *
 */
//TODO: where is this going?
public class CorePlugin {

    public final static CorePlugin CDA = new CorePlugin("cda", new String[]{"cda"});
    public final static CorePlugin CDB = new CorePlugin("cdb", new String[]{"cdb"});
    public final static CorePlugin CDC = new CorePlugin("cdc");
    public final static CorePlugin CDE = new CorePlugin("pentaho-cdf-dd", new String[]{"cdfde"});
    public final static CorePlugin CDF = new CorePlugin("pentaho-cdf", new String[]{"wcdf"});
    public final static CorePlugin CDV = new CorePlugin("cdv");
    protected String name;
    protected String id;//title
    //TODO: why reimplement file associations here?
    private String[] fileExtensions;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getTitle() {
        return getId();
    }

    @Deprecated
    public CorePlugin(String id, String[] fileExtensions) {
        this(id);
        this.fileExtensions = fileExtensions;
    }

    public CorePlugin(String name, String title) {
        this.name = name;
        this.id = title;
    }

    public CorePlugin(String id) {
        this.name = id;
        this.id = id;
    }

    public CorePlugin() {
        this.name = "defaultName";
        this.id = "defaultId";
    }

    @JsonIgnore
    @Deprecated
    public IRepositoryFileFilter getPluginFileFilter() {
        return new IRepositoryFileFilter() {
            @Override
            public boolean accept(IRepositoryFile irf) {
                if (getFileExtensions() != null) {
                    for (String extension : getFileExtensions()) {
                        if (irf.getExtension().contains(extension)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the fileExtensions
     */
    @Deprecated
    @JsonIgnore
    public String[] getFileExtensions() {
        return fileExtensions;
    }

    /**
     * @param fileExtensions the fileExtensions to set
     */
    @Deprecated
    public void setFileExtensions(String[] fileExtensions) {
        this.fileExtensions = fileExtensions;
    }
}