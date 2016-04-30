package RayTracing;

import java.awt.Transparency;
import java.awt.color.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import RayTracing.Common.MathHelper;
import RayTracing.DM.*;
import RayTracing.DM.Math.*;

import javax.imageio.ImageIO;

/**
 * Main class for ray tracing exercise.
 */
public class RayTracer {

	public int imageWidth;
	public int imageHeight;
	public Camera camera;
	public Settings settings;
	public ArrayList<Material> materials;
	public ArrayList<Surface> surfaces;
	public ArrayList<Light> lights;

	/**
	 * Runs the ray tracer. Takes scene file, output image file and image size
	 * as input.
	 * 
	 * @throws RayTracerException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, RayTracerException {
		//
		// try {

		RayTracer tracer = new RayTracer();

		// Default values:
		tracer.imageWidth = 500;
		tracer.imageHeight = 500;

		// if (args.length < 2)
		// throw new RayTracerException(
		// "Not enough arguments provided. Please specify an input scene file
		// and an output image file for rendering.");

		String sceneFileName = args[0];
		String outputFileName = args[1];

		if (args.length > 3) {
			tracer.imageWidth = Integer.parseInt(args[2]);
			tracer.imageHeight = Integer.parseInt(args[3]);
		}

		// Parse scene file:
		tracer.parseScene(sceneFileName);

		// Render scene:
		tracer.renderScene(outputFileName);

		// } catch (IOException e) {
		// System.out.println(e.getMessage());
		// } catch (RayTracerException e) {
		// System.out.println(e.getMessage());
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// }

	}

	/**
	 * Parses the scene file and creates the scene. Change this function so it
	 * generates the required objects.
	 */
	public void parseScene(String sceneFileName) throws IOException, RayTracerException {
		FileReader fr = new FileReader(sceneFileName);

		BufferedReader r = new BufferedReader(fr);
		String line = null;
		int lineNum = 0;
		System.out.println("Started parsing scene file " + sceneFileName);
		materials = new ArrayList<Material>();
		surfaces = new ArrayList<Surface>();
		lights = new ArrayList<Light>();

		while ((line = r.readLine()) != null) {
			line = line.trim();
			++lineNum;

			if (line.isEmpty() || (line.charAt(0) == '#')) { // This line in the
																// scene file is
																// a comment
				continue;
			} else {
				String code = line.substring(0, 3).toLowerCase();
				// Split according to white space characters:
				String[] params = line.substring(3).trim().toLowerCase().split("\\s+");

				if (code.equals("cam")) {
					camera = new Camera(ArrayToPoint(params, 0), ArrayToPoint(params, 3), ArrayToVector(params, 6),
							Double.parseDouble(params[9]), Double.parseDouble(params[10]));
					System.out.println(String.format("Parsed camera parameters (line %d)", lineNum));
				} else if (code.equals("set")) {
					settings = new Settings(ArrayToColor(params, 0), Integer.parseInt(params[3]),
							Integer.parseInt(params[4]));
					System.out.println(String.format("Parsed general settings (line %d)", lineNum));
				} else if (code.equals("mtl")) {
					materials.add(new Material(ArrayToColor(params, 0), ArrayToColor(params, 3),
							ArrayToColor(params, 6), Double.parseDouble(params[9]), Double.parseDouble(params[10])));
					System.out.println(String.format("Parsed material (line %d)", lineNum));
				} else if (code.equals("sph")) {
					Sphere sphere = new Sphere(ArrayToPoint(params, 0), Double.parseDouble(params[3]));
					sphere.Material = materials.get(Integer.parseInt(params[4]) - 1);
					surfaces.add(sphere);
					System.out.println(String.format("Parsed sphere (line %d)", lineNum));
				} else if (code.equals("pln")) {
					Plane plane = new Plane(ArrayToVector(params, 0), Double.parseDouble(params[3]));
					plane.Material = materials.get(Integer.parseInt(params[4]) - 1);
					surfaces.add(plane);
					System.out.println(String.format("Parsed plane (line %d)", lineNum));
				} else if (code.equals("cyl")) {
					Cylinder cylinder = new Cylinder(ArrayToPoint(params, 0), Double.parseDouble(params[3]),
							Double.parseDouble(params[4]), ArrayToVector(params, 5));
					cylinder.Material = materials.get(Integer.parseInt(params[8]) - 1);
					surfaces.add(cylinder);
					System.out.println(String.format("Parsed cylinder (line %d)", lineNum));
				} else if (code.equals("lgt")) {
					lights.add(
							new Light(ArrayToPoint(params, 0), ArrayToColor(params, 3), Double.parseDouble(params[6]),
									Double.parseDouble(params[7]), Double.parseDouble(params[8])));
					System.out.println(String.format("Parsed light (line %d)", lineNum));
				} else {
					System.out.println(String.format("ERROR: Did not recognize object: %s (line %d)", code, lineNum));
				}
			}
		}
		// TODO: Add Validation
		// It is recommended that you check here that the scene is valid,
		// for example camera settings and all necessary materials were defined.

		System.out.println("Finished parsing scene file " + sceneFileName);

	}

	private Color ArrayToColor(String[] array, int index) {
		return new Color(Double.parseDouble(array[index]), Double.parseDouble(array[index + 1]),
				Double.parseDouble(array[index + 2]));
	}

	private Vector ArrayToVector(String[] array, int index) {
		return new Vector(Double.parseDouble(array[index]), Double.parseDouble(array[index + 1]),
				Double.parseDouble(array[index + 2]));
	}

	private Point ArrayToPoint(String[] array, int index) {
		return new Point(Double.parseDouble(array[index]), Double.parseDouble(array[index + 1]),
				Double.parseDouble(array[index + 2]));
	}

	/**
	 * Renders the loaded scene and saves it to the specified file location.
	 */
	public void renderScene(String outputFileName) {
		long startTime = System.currentTimeMillis();

		// Create a byte array to hold the pixel data:
		byte[] rgbData = new byte[this.imageWidth * this.imageHeight * 3];
		double ratio = imageWidth / imageHeight;
		camera.ScreenHeight = camera.ScreenWidth * (1 / ratio);
		double sacle = (camera.ScreenHeight / 2) / camera.ScreenDistance;
		int lastPercent = -1;
		for (int i = 0; i < imageWidth; i++) {
			for (int j = 0; j < imageHeight; j++) {
				if (i == 250 && j == 236)
					sacle = (camera.ScreenHeight / 2) / camera.ScreenDistance;
				sacle++;
				Ray ray = constructRayThroughPixel(i, j);
				Color color = getColor(ray, settings.MaxRecursion, null);
				rgbData[(j * this.imageWidth + i) * 3] = color.getRInByte();
				rgbData[(j * this.imageWidth + i) * 3 + 1] = color.getGInByte();
				rgbData[(j * this.imageWidth + i) * 3 + 2] = color.getBInByte();
			}
			int percent = i*100/imageWidth;
			if (percent != lastPercent) {
				System.out.println("Rendering . . .  " + percent + "%");
				lastPercent = percent;
			}
		}

		long endTime = System.currentTimeMillis();
		Long renderTime = endTime - startTime;

		// The time is measured for your own conveniece, rendering speed will
		// not affect your score
		// unless it is exceptionally slow (more than a couple of minutes)
		System.out.println("Finished rendering scene in " + renderTime.toString() + " milliseconds.");

		// This is already implemented, and should work without adding any code.
		saveImage(this.imageWidth, rgbData, outputFileName);

		System.out.println("Saved file " + outputFileName);

	}

	private Ray constructRayThroughPixel(int i, int j) {

		Vector w = camera.Direction;
		Vector u = MathHelper.crossProduct(w, camera.Up);
		Vector v = MathHelper.crossProduct(u, w);

		double pixelW = camera.ScreenWidth / imageWidth;
		double pixelH = camera.ScreenHeight / imageHeight;

		Vector wDelta = (Vector) w.scalarMult(camera.ScreenDistance); 	
		Vector uDelta = (Vector) u.scalarMult(pixelW * ((imageWidth / 2) - i + 0.5));
		Vector vDelta = (Vector) v.scalarMult(pixelH * ((imageHeight / 2) - j + 0.5));

		Point res = (Point) camera.Position.add(wDelta).add(uDelta).add(vDelta);

		return new Ray(res, MathHelper.getNormalizeVector(camera.Position, res));

	}

	private Color getColor(Ray ray, int recursionNum, Surface currentSurface) {
		if (recursionNum == 0)
			return settings.Background;
		Intersection intersection = getMinIntersection(ray, currentSurface);
		if (intersection == null)
			return settings.Background;
		Surface surface = intersection.getSurface();

		Color diffuseColor = new Color();
		Color specularColor = new Color();
		if (surface.Material.Transparency != 1) {
			for (Light light : lights) {
				diffuseColor = diffuseColor.addColor(getColor(light, ray, intersection));
				specularColor = specularColor.addColor(getSpecularColor(light, ray, intersection));
			}
			specularColor = diffuseColor.multColor(specularColor.multColor(surface.Material.Specular));
			diffuseColor = diffuseColor.multColor(surface.Material.Diffuse);
		}

		Color backgroundColor = new Color();
		if (surface.Material.Transparency != 0)
			backgroundColor = getColor(new Ray(intersection.getPoint(), ray.getDirection()), recursionNum - 1, surface);

		Color reflectionColor = new Color();
		if (!surface.Material.Reflection.equals(new Color()))
			reflectionColor = getReflectionColor(ray, recursionNum, intersection, surface);

		return backgroundColor.mult(surface.Material.Transparency)
				.addColor(diffuseColor.addColor(specularColor).mult(1 - surface.Material.Transparency))
				.addColor(reflectionColor);
	}

	private Color getReflectionColor(Ray ray, int recursionNum, Intersection intersection, Surface surface) {

		Vector normal = intersection.getSurface().getNormal(intersection.getPoint(), ray.getDirection());

		Vector reflectionVector = (Vector) normal.scalarMult(2 * normal.dotProduct(ray.getDirection()))
				.substruct(ray.getDirection());
		reflectionVector = (Vector) reflectionVector.normalize().scalarMult(-1);
		Ray reflectionRay = new Ray(intersection.getPoint(), normal);
		Color reflectionColor = getColor(new Ray(reflectionRay.getPointOnRay(0), reflectionVector), recursionNum - 1,
				surface).multColor(surface.Material.Reflection);

		return reflectionColor;
	}

	private Color getSpecularColor(Light light, Ray ray, Intersection intersection) {

		Vector normal = intersection.getSurface().getNormal(intersection.getPoint(), ray.getDirection());
		Vector l = (Vector) MathHelper.getNormalizeVector(intersection.getPoint(), light.Position).scalarMult(-1);
		Vector r = (Vector) normal.scalarMult(2 * normal.dotProduct(l)).substruct(l);
		r = (Vector) r.normalize().scalarMult(-1);
		Vector v = (Vector) ray.getDirection().scalarMult(-1);
		double theta = v.dotProduct(r);
		if (theta < 0)
			return new Color();
		double phong = Math.pow(theta, intersection.getSurface().Material.Phong);
		return light.Color.mult(phong * light.SpecularIntensity);
	}

	private Color getColor(Light light, Ray ray, Intersection intersection) {
		Vector normalPlane = MathHelper.getNormalizeVector(light.Position, intersection.getPoint());
		double offsetPlane = normalPlane.dotProduct(light.Position);
		double sumOfPoint = Math.pow(settings.ShadowRays, 2);

		Vector shadowColors = new Vector();
		Vector w = normalPlane;
		Vector up = findPlaneUpVector(normalPlane, offsetPlane, light.Position);
		Vector u = MathHelper.crossProduct(w, up);
		if (u.equals(new Vector()))
			u = up;
		Vector v = MathHelper.crossProduct(u, w);

		double pixel = light.Radius / settings.ShadowRays;
		Random rnd = new Random();
		for (int i = 0; i < settings.ShadowRays; i++) {
			for (int j = 0; j < settings.ShadowRays; j++) {

				Vector uDelta = (Vector) u.scalarMult(pixel * ((settings.ShadowRays / 2) - i + rnd.nextDouble()));
				Vector vDelta = (Vector) v.scalarMult(pixel * ((settings.ShadowRays / 2) - j + rnd.nextDouble()));

				Point res = (Point) light.Position.add(uDelta.add(vDelta));

				Ray shadowRay = new Ray(res, MathHelper.getNormalizeVector(res, intersection.getPoint()));
				Vector shadowColor = getShdowColor(light, intersection, res, shadowRay);
				shadowColors = (Vector) shadowColors.add(shadowColor);
			}
		}
		shadowColors = (Vector) shadowColors.scalarMult(1 / sumOfPoint);
		Color lightColor = new Color(shadowColors.getCoordinate(0), shadowColors.getCoordinate(1),
				shadowColors.getCoordinate(2));
		Vector normal = intersection.getSurface().getNormal(intersection.getPoint(), ray.getDirection());
		return lightColor.mult(Math.abs(normal.dotProduct(normalPlane.scalarMult(-1))));
	}

	private Vector getShdowColor(Light light, Intersection intersection, Point res, Ray shadowRay) {
		ArrayList<Intersection> intersections = getCloserIntersection(shadowRay, null,
				intersection.getPoint().calcDistance(res));
		Vector shadowColor = new Vector(light.Color.getR(), light.Color.getG(), light.Color.getB());
		boolean hasShadow = false;
		double shadow = 1.0;
		for (Intersection intersection2 : intersections) {
			if (intersection2.getPoint().calcDistance(intersection.getPoint()) >MathHelper.EPSILON) {
				// shadowColor = (Vector)
				// shadowColor.scalarMult(intersection2.getSurface().Material.Transparency);
				shadow *= intersection2.getSurface().Material.Transparency;
				hasShadow = true;
			}
		}
		if (hasShadow) {
			// shadowColor = (Vector) shadowColor.scalarMult();

			shadowColor = (Vector) shadowColor.scalarMult(Math.max(shadow, 1 - light.ShadowIntensity));
			/*
			 * if (shadow != 0) shadowColor = (Vector)
			 * shadowColor.scalarMult(Math.max(shadow,1 -
			 * light.ShadowIntensity); else shadowColor = (Vector)
			 * shadowColor.scalarMult(());
			 */

		}
		return shadowColor;
	}

	private Vector findPlaneUpVector(Vector normalPlane, double offsetPlane, Point position) {

		Point planePoint = null;
		double newValue = offsetPlane;
		if (offsetPlane == 0)
			newValue = 1;
		if (normalPlane.getCoordinate(0) != 0.0)
			planePoint = new Point(newValue, 0, 0);
		else if (normalPlane.getCoordinate(1) != 0.0)
			planePoint = new Point(0, newValue, 0);
		else
			planePoint = new Point(0, 0, newValue);
		return MathHelper.getNormalizeVector(position, planePoint);

	}

	private Intersection getMinIntersection(Ray ray, Surface currentSurface) {

		double minDistance = Double.MAX_VALUE;
		Intersection minIntersection = null;
		for (Surface surface : surfaces) {
			if (currentSurface != surface) {
				Intersection surfaceIntersection = surface.findIntersection(ray);
				if (surfaceIntersection != null) {
					double distance = surfaceIntersection.getDistance();
					if (minDistance > distance && distance >= 0) {
						minDistance = surfaceIntersection.getDistance();
						minIntersection = surfaceIntersection;
					}
				}
			}
		}
		return minIntersection;
	}

	private ArrayList<Intersection> getCloserIntersection(Ray ray, Surface currentSurface, double distance) {

		ArrayList<Intersection> intersections = new ArrayList<Intersection>();
		for (Surface surface : surfaces) {
			Intersection surfaceIntersection = surface.findIntersection(ray);
			if (surfaceIntersection != null) {
				if (surfaceIntersection != null) {
					double newDistance = surfaceIntersection.getDistance();
					if (newDistance > 0 && distance > newDistance) {
						intersections.add(surfaceIntersection);
					}
				}
			}
		}
		return intersections;
	}

	// ////////////////////// FUNCTIONS TO SAVE IMAGES IN PNG FORMAT
	// //////////////////////////////////////////

	/*
	 * Saves RGB data as an image in png format to the specified location.
	 */
	public static void saveImage(int width, byte[] rgbData, String fileName) {
		try {

			BufferedImage image = bytes2RGB(width, rgbData);
			ImageIO.write(image, "png", new File(fileName));

		} catch (IOException e) {
			System.out.println("ERROR SAVING FILE: " + e.getMessage());
		}

	}

	/*
	 * Producing a BufferedImage that can be saved as png from a byte array of
	 * RGB values.
	 */
	public static BufferedImage bytes2RGB(int width, byte[] buffer) {
		int height = buffer.length / width / 3;
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
		ColorModel cm = new ComponentColorModel(cs, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
		SampleModel sm = cm.createCompatibleSampleModel(width, height);
		DataBufferByte db = new DataBufferByte(buffer, width * height);
		WritableRaster raster = Raster.createWritableRaster(sm, db, null);
		BufferedImage result = new BufferedImage(cm, raster, false, null);

		return result;
	}

	public static class RayTracerException extends Exception {
		public RayTracerException(String msg) {
			super(msg);
		}
	}

}