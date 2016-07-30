package edu.jsykora.sql2stream;

// TODO: Auto-generated Javadoc

class ReferenceObject<R> {

    private R r;

    private String alias;

    protected ReferenceObject(R r, String alias) {
        this.r = r;
        this.alias = alias;
    }

    public R getR() {
        return r;
    }

    public String getAlias() {
        return alias;
    }
}
