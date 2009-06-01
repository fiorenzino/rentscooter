@org.hibernate.annotations.NamedQueries( {
		@org.hibernate.annotations.NamedQuery(name = "GET_RESERVATIONS_BY_DATA", query = "SELECT p FROM Prenotazione p WHERE (p.singleDay >= :DAL) AND (p.singleDay <= :AL) ORDER BY p.singleDay"),
		@org.hibernate.annotations.NamedQuery(name = "GET_RESERVATIONS_BY_DATA_AND_IDSCOOTER", query = "SELECT p FROM Prenotazione p WHERE p.contratto.scooter.id = :IDSCOOTER AND (p.singleDay >= :DAL) AND (p.singleDay <= :AL) ORDER BY p.singleDay"),
		@org.hibernate.annotations.NamedQuery(name = "GET_RESERVATIONS_BY_DATA_AND_CILINDRATA", query = "SELECT p FROM Prenotazione p WHERE p.contratto.scooter.cilindrata = :CILINDRATA AND p.singleDay <= :AL AND p.singleDay >= :DAL order by p.singleDay"),
		@org.hibernate.annotations.NamedQuery(name = "GET_SCOOTER_BY_CILINDRATA", query = "select c from Scooter c WHERE c.cilindrata = :CILINDRATA order by c.marca, c.modello"),
		@org.hibernate.annotations.NamedQuery(name = "GET_ALL_SCOOTER", query = "select c from Scooter c order by c.marca, c.modello")})
package it.reservations.par;