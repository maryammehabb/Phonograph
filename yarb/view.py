from Model import Base, Reservation, Restaurant, User, Branch, Table, Complaint, Item, Admin, Order
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

engine = create_engine('sqlite:///PH.db')
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()

def view_Allreservation():

    for reservation in session.query(Reservation).all():
        print('ID:',reservation.id)
        print('User ID:',reservation.userID)
        print('Number of people:',reservation.numOfPeople)
        print('Branch ID:',reservation.branchID)
        print('Restaurant ID:',reservation.resID)
        print('Table ID:',reservation.tableID)
        print('Time reserved:',reservation.timeReserved)
        print('Time made:',reservation.timeMade)
        print("-------")

