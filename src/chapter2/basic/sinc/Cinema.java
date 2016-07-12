package chapter2.basic.sinc;

/**
 * Created by krp on 12.06.16.
 */
public class Cinema {
    private long vacanciesCinema1;
    private long vacanciesCinema2;

    private final Object controlCinema1 = new Object();
    private final Object controlCinema2 = new Object();

    public Cinema() {
        vacanciesCinema1 = 20;
        vacanciesCinema2 = 20;
    }

    public boolean sellTickets1(int number) {
        synchronized (controlCinema1) {
            if (number < vacanciesCinema1) {
                vacanciesCinema1-=number;
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean sellTickets2(int number) {
        synchronized (controlCinema2) {
            if (number < vacanciesCinema2) {
                vacanciesCinema2-=number;
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean returnTickets1(int number) {
        synchronized (controlCinema1) {
            vacanciesCinema1+=number;
            return true;
        }
    }

    public boolean returnTickets2(int number) {
        synchronized (controlCinema2) {
            vacanciesCinema2+=number;
            return true;
        }
    }

    public long getVacanciesCinema1() {
        return vacanciesCinema1;
    }

    public long getVacanciesCinema2() {
        return vacanciesCinema2;
    }
}

class TicketOfficer1 implements Runnable {

    private Cinema cinema;

    public TicketOfficer1(Cinema cinema) {
        this.cinema = cinema;
    }

    @Override
    public void run() {
        cinema.sellTickets1(3);
        cinema.sellTickets1(2);
        cinema.sellTickets2(2);
        cinema.returnTickets1(3);
        cinema.sellTickets1(5);
        cinema.sellTickets2(2);
        cinema.sellTickets2(2);
        cinema.sellTickets2(2);
    }
}

class TicketOfficer2 implements Runnable {

    private Cinema cinema;

    public TicketOfficer2(Cinema cinema) {
        this.cinema = cinema;
    }

    @Override
    public void run() {
        cinema.sellTickets2(2);
        cinema.sellTickets2(4);
        cinema.sellTickets1(2);
        cinema.sellTickets1(1);
        cinema.returnTickets2(2);
        cinema.sellTickets1(3);
        cinema.sellTickets2(2);
        cinema.sellTickets1(2);
    }
}

class MainCinema {
    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        TicketOfficer1 ticketOfficer1 = new TicketOfficer1(cinema);
        Thread ticketOfficer1Thread = new Thread(ticketOfficer1, "TicketOfficer1");

        TicketOfficer1 ticketOfficer2 = new TicketOfficer1(cinema);
        Thread ticketOfficer2Thread = new Thread(ticketOfficer2, "TicketOfficer1");

        ticketOfficer1Thread.start();
        ticketOfficer2Thread.start();

        try {
            ticketOfficer1Thread.join();
            ticketOfficer2Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Room one Vacancies: %d\n", cinema.getVacanciesCinema1());
        System.out.printf("Room two Vacancies: %d\n", cinema.getVacanciesCinema2());
    }
}