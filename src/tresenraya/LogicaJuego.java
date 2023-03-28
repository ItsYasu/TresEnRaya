package tresenraya;

import javax.swing.*;
import java.awt.*;

public class LogicaJuego {
    int turno, pX, pO; // Turno del jugador
    boolean habilitado; // Habilita y deshabilita el tablero

    /**
     * Inicializaremos el juego con las siguientes variables_
     *
     * @param turno (Nos indicará el turno del jugador: 0 - X, 1 - O)
     * @param pX    (Contiene el valor total de las victorias de este jugador)
     * @param pO    (Contiene el valor total de las victorias de este jugador)
     */
    public LogicaJuego(int turno, int pX, int pO) {
        this.turno = turno;
        this.pX = pX;
        this.pO = pO;
    }

    /**
     * Obtener turno
     *
     * @return
     */
    public int getTurno() {

        return turno;
    }

    /**
     * Insertar turno
     *
     * @param turno
     */
    public void setTurno(int turno) {

        this.turno = turno;
    }

    public int getpX() {

        return pX;
    }

    public void setpX(int pX) {
        this.pX = pX;
    }

    public int getpO() {
        return pO;
    }

    public void setpO(int pO) {
        this.pO = pO;
    }

    /**
     * Llamaremos a este método para cambiar de turno
     */
    public void cambioTurno() {
        if (getTurno() == 0) {
            setTurno(1);
        } else if (getTurno() == 1) {
            setTurno(0);
        } else {
            System.out.println("Turno incorrecto");
        }
    }
    /**
     * Comprobar si se ha conseguido un tres en raya,
     * en caso que se haya conseguido devolverá 1, en caso contrario retorna 0 y continúa el juego
     *
     * @param matriz (Tablero de juego)
     * @return
     */
    public int comprobarJuego(int matriz[][]) {
    /* se tendrá que comprobar que las posiciones correspondientes tengan el 
       mismo valor; es decir se tiene que ir comparando las casillas entre sí; 
       de forma horizontal, vertical y diagonal, obligatoriamente.  . 
       Se evalúa utilizar las estructuras correctas, para reducir el número de 
       iteraciones. Todo de forma organizada y optimizada recordemos 
       que estamos en un módulo del tercer semestre del ciclo.  Pero, 
       por si acaso posible pseudocódigo:   
        Comprobar si existe tres en raya:
            Comprobar horizontal			
            si no, Comprobar vertical        
            si no, Comprobar en diagonal
        Si no hay tres en raya:
    */
        // Inserta código aquí...
        int N = matriz.length;

        // Comprobación horitzontal. Comprueba si hay tres elementos iguales en una fila
        for (int i = 0; i < N; i++) { //este bucle solo reitera sobre las filas (se ignoran columnas vacias)
            if (matriz[i][0] != 0 && matriz[i][0] == matriz[i][1] && matriz[i][1] == matriz[i][2]) {
                return 1; //el valor 0 (matriz[i][0] != 0) se considera nulo. Es decir que no haya nada en la casilla
            }
        }

        // Comprobación vertical. Comprueba si hay tres elementos iguales en una columna
        for (int j = 0; j < N; j++) { //este bucle solo reitera sobre las columnas (se ignoran columnas vacias)
            if (matriz[0][j] != 0 && matriz[0][j] == matriz[1][j] && matriz[1][j] == matriz[2][j]) {
                return 1;
            }
        }

        // Comprobación diagonal de izq. a der. (se ignoran casillas vacias)
        if (matriz[0][0] != 0 && matriz[0][0] == matriz[1][1] && matriz[1][1] == matriz[2][2]) {
            return 1;
        }

        // Comprobación diagonal de der. a izq. (se ignoran casillas vacias)
        if (matriz[0][2] != 0 && matriz[0][2] == matriz[1][1] && matriz[1][1] == matriz[2][0]) {
            return 1;
        }

        // Si no hay tres en raya, devolver 0
        return 0;
    }


    /**
     * En caso de Ganador, mostrará la ventana, dentro del tablero; con el
     * mensaje de enhorabuena al gandador; y le preguntará si desea continuar
     * jugando.
     * Se valorará el código limpio, comprensible, organizado y optimizado;
     * se puede implementar en 3 o 4 líneas de código
     * Obligatorio utilizar el operador condicional ?
     *
     * @param jp (panel donde está situado el tablero)
     *           variable local String mensajeGanador
     */
    public void mostrarVentanaGanador(javax.swing.JPanel jp) {
        Juego juego = new Juego();
        int opcion = JOptionPane.showOptionDialog(jp, "¡Enhorabuena, jugador, has ganado. ¿Desea continuar jugando?", "Ganador", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        //opcion = (opcion == JOptionPane.YES_OPTION) ? iniciarJuego() : System.exit(0);
        //No usamos if-else porque es obligatorio usar un conidcional ternario (?)
        //If-else para comprobar.
        if (opcion == JOptionPane.YES_OPTION) {
            juego.iniciarJuego();
        } else {
            System.exit(0);
        }

    }
    /**
     * Deshabilitará el botón para evitar que se vuelva a posicionar una ficha en ese hueco
     *
     * @param bt     (Botón seleccionado)
     * @param x      (Posición x en el tablero)
     * @param y      (Posición y en el tablero)
     * @param matriz (Tablero de juego)
     * @param jp     (Panel dónde se sitúa el tablero de juego)
     * @param lX     (JLabel del jugador X)
     * @param lO     (JLabel del jugador O)
     * @return
     */
    public int tiradaJugador(javax.swing.JButton bt, int x, int y, int matriz[][],
                             javax.swing.JPanel jp, javax.swing.JLabel lX, javax.swing.JLabel lO) {
        // Inserta código aquí...
        Juego juego = new Juego();
        // Deshabilita el botón
        bt.setEnabled(false);

        // Insertar la ficha en el botón, para ello llamamos al método ponerficha
        ponerFicha(matriz, x, y, bt, getTurno());

        // Comprobar si se ha ganado la partida
        if (comprobarJuego(matriz) != 0) {
            // Si hay un ganador
            ganador(lX, lO);
            // Deshabilitar el tablero
            habilitado = false;
            habilitarTablero(jp);
            juego.clearButtons();
            // Llamamos método para mostrar ventana ganador
            mostrarVentanaGanador(jp);

        } else {
            // Continuaría el juego
            cambioTurno();
        }

        return 0;
    }




    /**
     * Actualizar la puntuación de cada jugador al ganar la partida
     * Al finalizar el incremento de puntuación es necesario cambiar de turno
     *
     * @param lX (JLabel del jugador X)
     * @param lO (JLabel del jugador O)
     */
    public void ganador(javax.swing.JLabel lX, javax.swing.JLabel lO) {
        // Inserta código aquí...

        // Incrementa jugador ganador e inserta resultado en jLabel
        if (getTurno() == 0) {
            pX++;
            lX.setText("" + pX);
        } else {
            pO++;
            lO.setText("" + pO);
        }
        cambioTurno();//Para que inicie la partida el que perdió.
    }



    /**
     * Habilitará o deshabilitará el tablero dependiendo del estado de la variable habilitado
     *
     * @param jp (Panel dónde se sitúa el tablero de juego)
     */
    public void habilitarTablero(javax.swing.JPanel jp) {
        Component[] componentes = jp.getComponents(); // Obtenemos los componentes del panel
        for (Component componente : componentes) {
            componente.setEnabled(habilitado); // Habilitamos o deshabilitamos los componentes según el valor de la variable habilitado
        }
    }


    /**
     * En ponerFicha, Insertaremos la ficha en la posición correspondiente de la matriz
     * Llamaremos al método pintarFicha
     *
     * @param matriz (Tablero de juego)
     * @param x      (Posición x en el tablero)
     * @param y      (Posición y en el tablero)
     * @param bt     (Botón pulsado)
     *               turno  (para saber que ficha poner)
     */
    //En este método se pone una ficha asociada al jugador que la ha puesto, por ejemplo,
    //si la posición (x,y) corresponde al jugador/turno 1, se asignara valor 1, lo contrario, valor 2.
    public void ponerFicha(int matriz[][], int x, int y, javax.swing.JButton bt, int turno) {
        matriz[x][y] = turno; // Asignar el valor correspondiente a la posición en la matriz
        pintarFicha(bt, turno); // Llamar al método pintarFicha() para mostrar la ficha correspondiente en el botón
    }


    /**
     * Pintará la ficha en el tablero de juego visual, es decir, en el botón
     *
     * @param bt (Botón pulsado)
     */
    //Este método cambia el color y el texto del botón "bt" en función del turno que sea.
    private void pintarFicha(javax.swing.JButton bt, int turno) {
        // Si el turno es de 0, mostrará una X en rojo
        if (turno == 0) {
            bt.setForeground(Color.red);
            bt.setText("X");
        }
        // Si el turno es de 1, mostrará una O en azul
        else {
            bt.setForeground(Color.blue);
            bt.setText("O");
        }
    }
    /**
     * Inicializa una nueva partida, reinicia la matriz (Tablero de juego) y habilita el tablero
     * <p>
     * ¡¡¡¡No es necesario modificar este método!!!!.
     *
     * @param matriz (Tablero de juego)
     * @param jp     (Panel dónde se sitúa el tablero de juego)
     */
    public void iniciarPartida(int matriz[][], javax.swing.JPanel jp) {
        // Rellenamos la matriz por primera vez, evitando que se repitan los números
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                matriz[x][y] = (x + 10) * (y + 10);
            }
        }

        // Habilitar tablero
        habilitado = true;
        habilitarTablero(jp);
    }
}
