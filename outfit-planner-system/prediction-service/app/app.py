from flask import Flask, request, redirect, url_for, jsonify, render_template
from flask_cors import CORS
from app.prediction_controller import prediction_controller
from werkzeug.exceptions import HTTPException

app = Flask(__name__)

app.register_blueprint(prediction_controller)
cors = CORS(app, resources={r"/*": {"origins": "*"}})
