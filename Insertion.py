from Model import Base, Reservation, Restaurant, User, Branch, Table, Complaint, Item, Admin, Order
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

engine = create_engine('sqlite:///PH.db')
Base.metadata.bind = engine
DBSession = sessionmaker(bind=engine)
session = DBSession()


def add_restaurant( name, ts, te,km):
    restaurant = Restaurant( name=name, timeStart=ts, timeEnd=te,kidsMenue=km)
    session.add(restaurant)
    session.commit()


def add_branch( address, numOFTables, delivery, resID,ka , sm):
    branch = Branch( numOFTables=numOFTables, delivery=delivery, resID=resID, address=address, kidsArea=ka, smokingArea=sm)
    session.add(branch)
    session.commit()


def add_table( branchID, numOfSeats, reserved):
    table = Table( branchID=branchID, numOfSeats=numOfSeats, reserved=reserved)
    session.add(table)
    session.commit()


def add_compliant( branchID, resID, file, uID):
    compliant = Complaint(branchID=branchID, resID=resID, file=file, user_ID=uID)
    session.add(compliant)
    session.commit()


def add_item(resID, name, price, orderID,kids):
    item = Item( resID=resID, name=name, price=price, orderID=orderID,kids=kids)
    session.add(item)
    session.commit()


def add_user( mail, password, name,phone, address):
    user = User( mail=mail, password=password, name=name,phone=phone,address=address)
    session.add(user)
    session.commit()


def add_admin(mail,password,resId):
    admin = Admin( resID=resId,password=password,mail=mail)
    session.add(admin)
    session.commit()


def add_reservation(userID, numofPeople, branchID, tableID, resID, tr, tm):
    reservation = Reservation(id=id, userID=userID, numofPeople=numofPeople, branchID=branchID, tableID=tableID,
                              resID=resID, timeReserved=tr, timeMade=tm)
    session.add(reservation)
    session.commit()


def add_order(time, delivery, price, resID, timeD, done, userID, content,Items, numOfOrders):
    order = Order(time=time, delivery=delivery, price=price, resID=resID, timeDelivered=timeD, done=done,
                  userID=userID, content=content, items=Items, numOfOrders=numOfOrders)
    session.add(order)
    session.commit()


'''add_user("nour@gmail.com","nour","nour","1234","12 blablabla st")
add_user("mariamE@gmail.com","mariamE","mariam","123","13 blablabla st")
add_user("mariamA@gmail.com","mariamA","mariam","12","14 blablabla st")
add_user("nagham@gmail.com","nagham","nagham","1234","15 blablabla st")
add_user("marwa@gmail.com","marwa","marwa","12345","16 blablabla st")
'''
#add_restaurant("nour","8 am","12 am",True)
#add_admin("admin@gmail.com","admin",1)
