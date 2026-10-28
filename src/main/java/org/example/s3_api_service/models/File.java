package org.example.s3_api_service.models;

/**
 * File is a representation of an actual file, including its contents as a byte array
 * This is currently limited to about 2Gbs
 */
public class File{
    private String name;
    private byte[] content;

    public File(String name, byte[] content){
        this.name = name;
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getExtension(){
        int extStart = this.name.indexOf(".");

        return this.name.substring(extStart);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
