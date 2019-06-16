from Model import Base, Reservation, Restaurant, User, Branch, Table, Complaint, Item, Admin, Order
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

engine = create_engine('sqlite:///PH.db')
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()


def edit_Resturant(id, name, ts, te, kids):
    restaurant = session.query(Restaurant).filter(Restaurant.id == id)
    if restaurant == None:
        return
    restaurant.name = name
    restaurant.timeStart = ts
    restaurant.timeEnd = te
    restaurant.kidsMenue = kids
    session.commit()


def edit_Item(resID, id, name, price, kids):
    item = session.query(Item).filter(Item.id == id)
    if item == None:
        return
    for i in item:
        if item.resID == resID:
            item.name = name
            item.price = price
            item.kids = kids
            item.kidsMenue = kids
            session.commit()


def edit_Order(resID, id, mnum, mname, time,delivery,price,delivered,uID):
    order = session.query(Order).filter(Order.id == id)
    if order== None:
        return
    for i in order:
        if order.resID == resID and order.userID == uID:
            order.numOfOrders=mnum
            order.items=mname
            order.time=time
            order.delivery=delivery
            order.price=price
            order.done=delivered
            order.userID=uID
            session.commit()


def edit_Reservation(resID, id,numOfPeolpe, branchID, tableID,tr, tm):
    reservation = session.query(Reservation).filter(Reservation.id == id)
    if Reservation== None:
        return
    for i in reservation:
        if reservation.resID == resID and reservation.branchID==branchID and reservation.tableID==tableID:
            reservation.numOfPeople=numOfPeolpe
            reservation.timeReserved=tr
            reservation.timeMade=tm
            session.commit()


def edit_Table(resID, id, bID,numOfSeats,reserved):
    table = session.query(Table).filter(Table.id == id)
    if table== None:
        return
    for i in table:
        if table.resID == resID and table.brachID==bID:
            table.numOfSeats=numOfSeats
            table.reserved=reserved
            session.commit()

