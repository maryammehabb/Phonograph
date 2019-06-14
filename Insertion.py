from Model import Base, Reservation, Restaurant, User, Customer, Branch, Table, Complaint, Item, Admin, Order
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

engine = create_engine('sqlite:///PH.db')
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()


def add_restaurant(id, name, ts, te,km):
    restaurant = Restaurant(id=id, name=name, timeStart=ts, timeEnd=te,kidsMenue=km)
    session.add(restaurant)
    session.commit()


def add_branch(id, address, numOFTables, delivery, resID,km , sm):
    branch = Branch(id=id, numOFTables=numOFTables, delivery=delivery, resID=resID, address=address, kidsArea=ka, smokingArea=sm)
    session.add(branch)
    session.commit()


def add_table(id, branchID, numOfSeats, reserved, branch):
    table = Table(id=id, branchID=branchID, numOfSeats=numOfSeats, reserved=reserved)
    session.add(table)
    session.commit()


def add_compliant(id, branchID, resID, file, cusID):
    compliant = Complaint(id=id, branchID=branchID, resID=resID, file=file, cusID=cusID)
    session.add(compliant)
    session.commit()


def add_item(Id, resID, name, price, orderID):
    item = Item(id=Id, resID=resID, name=name, price=price, orderID=orderID)
    session.add(item)
    session.commit()


def add_user(id, mail, password, discriminator):
    user = User(id=id, mail=mail, password=password, discriminator=discriminator)
    session.add(user)
    session.commit()


def add_customer(id, name, phone, address):
    customer = Customer(id=id, name=name, phone=phone, address=address)
    session.add(customer)
    session.commit()


def add_admin(id, resId):
    admin = Admin(id=id, resID=resId)
    session.add(admin)
    session.commit()


def add_reservation(id, cusID, numofPeople, branchID, tableID, resID, tr, tm):
    reservation = Reservation(id=id, cusID=cusID, numofPeople=numofPeople, branchID=branchID, tableID=tableID,
                              resID=resID, timeReserved=tr, timeMade=tm)
    session.add(reservation)
    session.commit()


def add_order(id, time, delivery, price, resID, timeD, done, cusID, content,Items, numOfOrders):
    order = Order(id=id, time=time, delivery=delivery, price=price, resID=resID, timeDelivered=timeD, done=done,
                  cusID=cusID, content=content, items=Items, numOfOrders=numOfOrders)
    session.add(order)
    session.commit()


#add_restaurant(3,"DSsrf","dfcfdsf", 'fdfe',"frefef")
