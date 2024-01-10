from flask import jsonify, request, Blueprint
from werkzeug.wrappers import Request
from app.prediction import predict
import json

prediction_controller = Blueprint('prediction', __name__)

@prediction_controller.route("/predict", methods=["post"])
def predict_outfits():
    req = request.get_json()
    predictions = predict(req['outfits'], req['weatherConditions'])
    predictions = {int(key): value for key, value in predictions.items()}

    return jsonify({"predictions": predictions})