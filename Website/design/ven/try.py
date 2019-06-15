import sqlite3
from flask import g, app, render_template, Flask

DATABASE = 'C:\/Users\Mariam\Desktop\PH.db'
app = Flask(__name__)

def get_db():
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DATABASE)
    return db

@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()



def retriveOrder():
    cur = get_db().cursor()
    cur.execute("SELECT * FROM Order")

    order = cur.fetchall()
    #print("hayyy", rows.)

    return render_template('index.html', order=order)
@app.route('/')
def retriveCustomer():
    cur = get_db().cursor()
    cur.execute("SELECT * FROM User")

    customer = cur.fetchall()
    #print("hayyy", rows.)
    return render_template('index.html', customer=customer)

def retriveReservation():
    cur = get_db().cursor()
    cur.execute("SELECT * FROM Reservation")

    res = cur.fetchall()
    #print("hayyy", rows.)

    return render_template('hey.html', res=res)

def retriveComplain():
    cur = get_db().cursor()
    cur.execute("SELECT * FROM complaint")

    complain = cur.fetchall()
    #print("hayyy", rows.)

    return render_template('hey.html', complain=complain)


def main():
    database = "C:\\sqlite\db\pythonsqlite.db"

    '''# create a database connection
    conn = create_connection(database)
    with conn:
        print("1. Query task by priority:")
        select_task_by_priority(conn, 1)'''
    retriveOrder()
    retriveCustomer()
    retriveReservation()
    retriveComplain()

if __name__ == "__main__":
    app.run(debug= True)
    main()