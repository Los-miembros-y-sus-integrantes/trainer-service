package co.analisys.biblioteca.model;

public class ResumenEntrenamiento {
    private int totalDuration;
    private int sessionCount;

    public ResumenEntrenamiento() {
        this.totalDuration = 0;
        this.sessionCount = 0;
    }

    public ResumenEntrenamiento actualizar(DatosEntrenamiento datos) {
        if (datos != null) {
            this.totalDuration += datos.getDuration();
            this.sessionCount += 1;
        }
        return this;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public int getSessionCount() {
        return sessionCount;
    }
}
